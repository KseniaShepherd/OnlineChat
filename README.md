# OnlineChat
(https://github.com/KseniaShepherd/OnlineChat/raw/master/https://cdn1.savepice.ru/uploads/2020/12/28/5d3c9cd0afaef15d44334f178eff1659-full.png)
Подключение сервера и клиентов происходит с учетом файла настроек. У сервера есть 2 исполняемых потока ( main  и SocketHandler) В main реализуется возможность подключиться к серверу в любой момент и присоединиться к чату, в SocketHandler процесс обмена сообщениями между клиентами с записью входящих сообщений в файл логирования (создается тут же). У клиента 3 исполняемых потока (main, MessageReader, MessageWriter). В main устанавливается соединение с указанным в настройках сервером.  В MessageReader непрерывно считывался входящие от сервера сообщения, в MessageWriter отправляются и записываются в созданный файл логирования сообщения полученные от клиента. При вводе “exit” закрываются все потоки и сокет соединение прекращает работу.
