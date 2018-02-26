#include <cstdlib>
#include <cstdio>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>
#include <error.h>
#include <netdb.h>
#include <sys/epoll.h>
#include <poll.h>
#include <thread>
#include <unordered_set>
#include <signal.h>

#define BUFSIZE 1024


void error(char *msg) {
  perror(msg);
  exit(1);
}


void run(){
    int sockfd;
    int portno=0;
    int clientlen;
    sockaddr_in serveraddr;
    sockaddr_in clientaddr;
    hostent *hostp;
    char buf[BUFSIZE];
    char *hostaddrp;
    int optval=1;
    int n;
    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if (sockfd < 0)
        error("ERROR opening socket");
    setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR,
        (const void *)&optval , sizeof(int));

    bzero((char *) &serveraddr, sizeof(serveraddr));
    serveraddr.sin_family = AF_INET;
    serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);
    serveraddr.sin_port = htons((unsigned short)portno);

    if (bind(sockfd, (struct sockaddr *) &serveraddr,
        sizeof(serveraddr)) < 0)
    error("ERROR on binding");
    clientlen = sizeof(clientaddr);


  while (true) {

    /*
     * recvfrom: receive a UDP datagram from a client
     */
    bzero(buf, BUFSIZE);
    n = recvfrom(sockfd, buf, BUFSIZE, 0,
		 (struct sockaddr *) &clientaddr, &clientlen);
    if (n < 0)
      error("ERROR in recvfrom");


    /*
     * sendto: echo the input back to the client
     */
    n = sendto(sockfd, buf, strlen(buf), 0,
	       (struct sockaddr *) &clientaddr, clientlen);
    if (n < 0)
      error("ERROR in sendto");
  }
}
