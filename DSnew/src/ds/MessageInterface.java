package ds;

//Reference: https://www.youtube.com/watch?v=GURClZeR96E&t=662s

//package DSProj3; 
//Aditya Ravikumar
//1001672163
import java.rmi.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

//This interface extends the remote interface which makes the following methods to be invoked remotely 
public interface MessageInterface extends Remote{
	
	//this method is invoked by the student process to send the student name and course name to message queue
	public void studentRequest(String name, String course) throws RemoteException;
	
	//this method is invoked by the advisor process to get the course list from message queue
	public Queue<String> advisorRequest() throws RemoteException;
	
	//this method is invoked by the advisor to send the response for each course to message queue
	public void advisorResponse( String course, String response) throws RemoteException ;
	
	//this method is invoked by the notification process to get the advisor response
	public ConcurrentHashMap<String, String>  getAdvisorResponse() throws RemoteException;
	
}

