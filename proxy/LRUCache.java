package proxy;

import java.util.HashMap;

/**
 * Created by prabhjotkaur on 09/04/2017.
 */
public class LRUCache {
    //Least Recently Used Policy is implemented to override cache//
    static int maxSize = 1024 * 5; // 5 MB
    static HashMap<String, Node> map = new HashMap<>(); // hash table is used to get the value in O(1)
    private static Node header;
    private static Node tail;

    public static void set(String urlToCall, String responseInfo) {
        //If urlCalled exists, update informtion and set header pointing to this node
        if(map.containsKey(urlToCall)){
            Node oldNode = map.get(urlToCall);
            oldNode.responseInfo = responseInfo;
            remove(oldNode);
            setHeader(oldNode);
        }else{
            //If urlcalled doesnt exist, create new node
            Node newNode = new Node(urlToCall, responseInfo);
            if(map.size()>= maxSize){
                //If cache max limit is reached, delete least recently used node stored in th tail.
                map.remove(tail.urlToCall );
                remove(tail);
                setHeader(newNode);
            }else{
                setHeader(newNode);
            }
            //Put new key,value in hash table
            map.put(urlToCall, newNode);
        }
    }

    public static void remove(Node node){
        if(node.previous !=null){
            node.previous.next = node.next;
        }else{
            header = node.next;
        }

        if(node.next!=null){
            node.next.previous = node.previous;
        }else{
            //updating tail to next least Recently used node
            tail = node.previous;
        }
    }

    public static void setHeader(Node node){
        node.next = header;
        node.previous = null;

        if(header !=null)
            header.previous = node;

        //Set header to recently used node
        header = node;

        //Set tail to least recently used node, it is set only once when tail is null
        if(tail ==null) {
            tail = header;
        }

    }

    public String get(String urlToCall) {
        if(map.containsKey(urlToCall)){
            Node n = map.get(urlToCall);
            remove(n);
            setHeader(n);
            return n.responseInfo;
        }
        return null;
    }


}
