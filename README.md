
# Distributed Content Searching
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Dillinger is a cloud-enabled, mobile-ready, offline-storage, AngularJS powered HTML5 Markdown editor.

  - Type some Markdown on the left
  - See HTML in the right
  - Magic

## Goals
Develop a simple overlay-based solution that allows a set of nodes to share contents (e.g., music files) among each other. Consider a set of nodes connected via some overlay topology. Each of the nodes has a set of files that it is willing to share with other nodes. Suppose node x is interested in a file f. x issues a  search query to the overlay to locate a at least one node y containing that particular file. Once the node is identified, the file f can be exchanged between X and y.
After completing this project, you will have developed a solution to search and download contents in a distributed system. You will be able to:
- design, develop, and debug overlay-based applications such as a simple search engine to find contents in a distributed system
- use RPCs, web services, or REST APIs to develop distributed systems
- measure an analyze the performance of a distributed system

## Challenge
The challenge consists of 4 phases. The 1st phase of the assignment is to generate the network topology and the contents of each node. The network will consist of 10+ nodes sharing 20 files among them, with each node contributing 3-5 files. Some files may be present in multiple nodes. Form the network and initialize the node contents as follows:

1. A new node that comes up gets connected to 2 randomly selected nodes existing in the distributed system. A Bootstrap Server (BS) is provided to facilitate this step.
- Following explains how to join and maintain the connectivity in the distributed system. See Section 4 for specific message formats used to talk to nodes and BS.

- 1.1.  Each new node added to the system will register at the given BS by providing node's IP address, port number, and user name. A unique username/key is essential to keep one student's nodes separate from another.
BS will respond only to the messages specified in Section 4.1. Thus, it should be used only to find nodes currently in the system. It will not respond to any network formation or query messages.
- 1.2.  The 1st node will receive only an acknowledgement (ACK) from the BS. The 2nd node will receive an ACK and the details of the first node. 3rd node will receive details of the first two nodes. 4th node will receive details of first three registered nodes, and so on (all nodes are sent from BS only to simplify debugging). However, from the 4th node on wards you should join only to 2 randomly selected nodes from the list of nodes you received.

- 1.3.  The new node joins the network via the 2 nodes learned from BS using the JOIN message, syntax of which is specified in Section 4.2. JOIN messages tell the contacted nodes that there is a new node in the system. UDP sockets are to be used to facilitate this communication.

- 1.4.  If your solution uses a routing/neighbor table, each node should either display it or should be able to do so upon request (when a command is issued).
If your program crashes, you will get a 9998 from BS when you try to register (REG) the same node again (unless you use a different IP or port). To simplify and maintain a consistent view of the distributed system at the BS (if this happens), your node needs to unregister before attempting to register again. If you are going to rerun the node with a different IP or port no, you have to issue an unregister request (UNREG) for the previous entry. You may do this through Telnet (only for testing and debugging) by manually issuing the command. You may issue 'PRINT' command through Telnet to see all the valid entries in the BS.
2. A list of file names is provided, and each node should be initialized with 3 to 5 randomly selected files from this list.
- Each node should either display the file names that it selected or should be able to do so upon request (when a command is issued).

The second phase is to design and develop a socket-based solution to find the files requested by different nodes. Query process is as follows:

1. Nodes generate requests for random file names (may or may not be in the given list).
2. Each request results in a query that is propagated in the network to find a node Y containing the file.
3. Node Y responds to the querying node with its address, which may be used to download the file.

Your solution for Phase 2 should satisfy the following requirements:

- Nodes will communicate using UDP and will follow the message format given in Section 4.
- The system should continue to operate, albeit with degraded performance, even when some nodes fail.
- Use the given list of file names and queries to demonstrate your solution.

The third phase is to extend your solution to support file transfer using either web services or REST API. Your solution in Phase 3 should satisfy the following requirements:

- Enable reliable transfer of the located file
- Suppose a file can be of any size between 2-10 MB 
-- Once download request is given randomly generate an integer between 2 - 10MB
-- Based on this number randomly generate some data as file content
-- Then calculate SHA hash for this data and display both file size and hash on console/window 
- For reliability you are allowed to use TCP based high-level communication solution such as web services or REST APIs. Among web services and REST API, you are recommended to extend the solution using that technology that you are not so familiar with.
- Update the Phase 2 design to reflect the web services or REST API based file download. No need to modify the communication with the BS or neighbors while setting up the network. 
- If needed, modify program in Phase 2 to reflect your file download implementation.

The final phase is to analyze the performance of the solutions developed in 2nd and 3rd phases
## Protocol
We will use a character-based protocol to make it easy to debug. You may issue commands through Telnet to the BS and other nodes to check whether commands are correctly responded. Each message starts with a command (in uppercase characters) that can be up to n characters long. Rest of the message will depend on the command. Each element in the command is separated by a white space.

### 3.1.  Register/Unregister With Bootstrap Server


##### Register Request message – used to register with the BS

length REG IP_address port_no username

     - e.g., 0036 REG 129.82.123.45 5001 1234abcd
     - length – Length of the entire message including 4 characters used to
       indicate the length. Always give length in xxxx format to make it
       easy to determine the length of the message.
     - REG – Registration request.
     - IP_address – IP address in xxx.xxx.xxx.xxx format. This is the IP
       address other nodes will use to reach you. Indicated with up to 15
       characters.
     - port_no – Port number. This is the port number that other nodes will
       connect to. Up to 5 characters.
     - Username – A string with characters and numbers. Register Response
       message – BS will send the following message
       
Register Response message – BS will send the following message
length REGOK no_nodes IP_1 port_1 IP_2 port_2

    e.g., 0051 REGOK 2 129.82.123.45 5001 64.12.123.190 34001
    
    length – Length of the entire message including 4 characters used to indicate the length. In xxxx format.
    REGOK – Registration response.
    no_ nodes – Number of node entries that are going to be returned by the registry
    0 – request is successful, no nodes in the system
    1 or 2 – request is successful, 1 or 2 nodes' contacts will be returned
    9999 – failed, there is some error in the command
    9998 – failed, already registered to you, unregister first
    9997 – failed, registered to another user, try a different IP and port
    9996 – failed, can’t register. BS full.
    IP_1 – IP address of the 1st node (if available).
    port_1 – Port number of the 1st node (if available).
    IP_2 – IP address of the 2nd node (if available).
    port_2 – Port number of the 2nd node (if available).
    To simplify the debugging BS will actually return all the nodes known to it. However, you should use only the first 2 nodes to connect.
    

Unregister Request message – used to unregister from the BS
length UNREG IP_address port_no username

    e.g., 0028 UNREG 64.12.123.190 432
    length – Length of the entire message including 4 characters used to indicate the length. In xxxx format.
    UNREG – Unregister request.
    IP_address – IP address in xxx.xxx.xxx.xxx format. This is the IP address other nodes will use to reach you. Indicated with up to 15 characters.
    port_no – Port number. This is the port number that other nodes will connect to. Up to 5 characters.
    username – A string with characters & numbers. Should be the same username used to register the node.
    

Unregister Response message – BS will send the following message
length UNROK value

    e.g., 0012 UNROK 0
    length – Length of the entire message including 4 characters used to indicate the length. In xxxx format.
    UNROK – Unregister response.
    value – Indicate success or failure.
    0 – successful
    9999 – error while unregistering. IP and port may not be in the registry or command is incorrect.
    For any message BS can't understand it will send an error of the format in Section 4.5.

Error Message
length ERROR

    0010 ERROR
    length – Length of the entire message including 4 characters used to indicate the length. In xxxx format.
    ERROR – Generic error message, to indicate that a given command is not understood. For storing and searching files/keys this should be send to the initiator of the message.

This text you see here is *actually* written in Markdown! To get a feel for Markdown's syntax, type some text into the left window and watch the results in the right.

### Credits 
This project is taken from Dr. Dilum Bandara and derived from Dilum Bandara and Anura P. Jayasumana, "Lab 4 – Searching Contents in a Distributed Application-Layer Network," ECE 658 – Internet Engineering class at Colorado State University, 2012. Further enhancements Vidarshana Bandara.

### Installation
----
In the You have to start the bootstrap server before running the program. 
Set the bootstrap properties in the `Bootstrap.properties` file.
Install the dependencies and devDependencies and start the server.
To create a jar and run

```sh
1.  In the DistributedFileSharing folder, run  `mvn clean package`
2.  Jar will be formed in the targer folder. You can simply run it by double clicking, or running it in a terminal with the following command.  `java -jar <filename>`
```
### Todos

 - Search Optimizations
 -  Performance Optimizations

License
----
MIT
