import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerClientThread extends Thread
{
	static List<PrintWriter> list = Collections  //새로운 클라이언트 올때마다 리스트에 등록! 하는것을 3번에 구현
			.synchronizedList(new ArrayList<PrintWriter>());

	Socket socket;

	PrintWriter writer;

	public PerClientThread(Socket socket)
	{
		this.socket = socket;

		try
		{
			writer = new PrintWriter(socket.getOutputStream());
            list.add(socket);
// (#3) add new client stream into list 소켓을 추가하면 된다 함수이름도 add라는 메소드를 사용해서 리스트에
          
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void run()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			while (true)
			{
				String str = reader.readLine();
				if (str == null)
				{
					break;
				}
				sendAll(str);
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		} finally
		{

// (#4) remove disconnected client from list 체점은 안함 

			try
			{
				socket.close();
			} catch (Exception ignored)
			{
			}
		}
	}

	private void sendAll(String str)
	{

		 for (PrintWriter writer : list){
			   writer.println(str);
			   writer.flush();
			  }
// (#5) broadcast arriving message to all clients  최소2줄이상 채팅문자열리 왔을때 가입자 모든 사람에게 리스트 콜랙션변수에서 하나씩 끄집어내서 문자열 보냄 브로드케스//트 for루프나 while루프
          

	}

}
