package cobalt.snake;

import java.io.IOException;
import java.io.PipedInputStream;

public class Input extends Thread{
	private static char command='r';
	PipedInputStream pis=null;
	
	public Input(PipedInputStream pis) {
		super();
		Input.command='r';
		this.pis = pis;
	}

	public static char getCommand() {
		return command;
	}
	
	@Override
	public void run(){
		int i=0;
		try {
			//'z'的值小于255，约定用255表示结束，然后用char的unicode值直接表示指令
			while((i=pis.read())!=255){
				command=(char)i;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				pis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
