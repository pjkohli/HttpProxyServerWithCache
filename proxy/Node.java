package proxy;

/**
 * Created by prabhjotkaur on 09/04/2017.
 */
public class Node {
    //Doubly Linked List with urlToCall and value//
    //Previous and Next pointing to previous and next node respectively//

    String urlToCall; // Url Called
    String responseInfo; // Response Information from the url called
    Node previous;
    Node next;

    public Node(String urlToCall, String responseInfo) {
        this.urlToCall = urlToCall;
        this.responseInfo = responseInfo;
    }
}
