package view.options;

import java.io.*;
import javax.sound.sampled.*;


public class MusicPlayer {
	

	private AudioFormat audioFormat;
	private AudioInputStream audioInputStream;
	private SourceDataLine sourceDataLine;
	

	public MusicPlayer(String musicFile) {  
		playAudio(musicFile);

	}
	
	
	//This method plays back audio data from an
	// audio file whose name is specified in the
	// parameter field.
	private void playAudio(String musicFile) {
		
	    try{
	      File soundFile = new File(musicFile);
	      audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	      audioFormat = audioInputStream.getFormat();
	      
	      System.out.println(audioFormat);

	      DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat);

	      sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);

	      // Create a thread to play back the data and
	      // start it running.  It will run until the
	      // end of file.
	      new PlayThread().start();
	    } 
	    catch (Exception e) {
	      e.printStackTrace();
	      System.exit(0);
	    }//end catch
  }
	

	// Inner class to play back the data from the
	// audio file.
	
	class PlayThread extends Thread {
		
	  private byte tempBuffer[] = new byte[10000];

	  public void run() {
		  
		  try{
			  sourceDataLine.open(audioFormat);
			  sourceDataLine.start();

			  int count;
			  
			  // Keep looping until the input read method
			  // returns -1 for empty stream.
			  while((count = audioInputStream.read(tempBuffer,0,tempBuffer.length)) != -1) {
				  
				  if(count > 0){
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
			  //System.exit(0);
		  }
	  	}
	}

}

