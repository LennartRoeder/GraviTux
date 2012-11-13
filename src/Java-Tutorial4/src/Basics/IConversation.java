package Basics;

/**
 * @author Knut Hartmann <BR>
 *         Flensburg University of Applied Sciences <BR>
 *         Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 14, 2012
 */

public interface IConversation {
	public void send(int senderID, int receiverID, String message);
	public String createMessage(int sourceID);
}
