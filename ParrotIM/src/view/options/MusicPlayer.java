package view.options;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import model.Model;

public class MusicPlayer {

    private AudioFormat audioFormat;
    private AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;

    public MusicPlayer(String musicFile, Model model) {
        try {
            if (model.getCurrentProfile().isSoundsEnabled()) {
                playAudio(musicFile);
            }
        } catch (NullPointerException e) {
            // Play if profile not loaded yet
            playAudio(musicFile);
        }
    }

    // This method plays back audio data from an
    // audio file whose name is specified in the
    // parameter field.
    private void playAudio(String musicFile) {

        try { 
            audioInputStream =
                    AudioSystem.getAudioInputStream(this.getClass()
                            .getResource(musicFile));
            audioFormat = audioInputStream.getFormat();

            DataLine.Info dataLineInfo =
                    new DataLine.Info(SourceDataLine.class, audioFormat);

            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

            // Create a thread to play back the data and
            // start it running. It will run until the
            // end of file.
            new PlayThread().start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }// end catch
    }

    // Inner class to play back the data from the
    // audio file.

    class PlayThread extends Thread {

        private byte tempBuffer[] = new byte[10000];

        public void run() {

            try {
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();

                int count;

                // Keep looping until the input read method
                // returns -1 for empty stream.
                while ((count =
                        audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {

                    if (count > 0) {
                        // Write data to the internal buffer of
                        // the data line where it will be
                        // delivered to the speaker.
                        sourceDataLine.write(tempBuffer, 0, count);
                    }
                }

                // Block and wait for internal buffer of the
                // data line to empty.
                sourceDataLine.drain();
                sourceDataLine.close();

            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
