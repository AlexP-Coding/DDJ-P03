Views:
    - ViewLogin
    - ViewGame
    - ViewGameTimer
    - ViewGameScores

    initView();
    getId + hostName();
    Socket socket = new socket();
    socket.askToPlay();
    int playerColor = socket.getResponse;
    initGame(); // starts board too
    Thread socketThread = new thread();

    while (!gameOver) {
        socketThread.awaitAndInterpretMsg();
        socketThread.awaitAndSendClick();
    }
    
