# Simple chat server
That is simple chat without authentication base on TCP socket.
## Download
### With compile code
Download repository <br/>
`` git clone https://github.com/mati2251/simple-chat-server`` <br/>
Run gradle <br/>
`` gradle `` <br/>
Run gradle jar <br/>
``gradle jar``</br>
Your jar file exist in build/libs directory
### Download from release
[Link to download](https://github.com/mati2251/simple-chat-server/releases)
## Usage
You can send two type request. <br>
If you want use chat. You must assign nick by request <br/>
``nick?yournickname`` <br/>
After that you can send message <br/>
``msg?to=nick,nick2&text=your message`` <br/>
to - nicks other user separate by `,` or if you want send msg to all user you will use `to=all` <br/>
text - your message
## License
GNU General Public License v3.0