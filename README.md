# HttpProxyServerWithCache
Http Proxy Server that services GET requests.

Least Recently Used(LRU) policy is used to cache information. 
LRU is used to  store the information from websites that were recently being requested while delete from the cache which is not being recently used. This is a great way of keeping updated information in the cache.
To implement LRU , doubly linked list is used to store the information. 
There are two nodes - header and tail to keep track of the most recently and least recently used nodes resepctively.
Header is updated to the new node added or updated.
Tailer is used to delete the information when cahe is full.
