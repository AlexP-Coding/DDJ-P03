    initBoard();
    socketAll = new socket();
    socketSingles[] = new List<socket>;

    while (!newGame) {
        msg = socketAll.awaitMsg();
        if (msg == newPlayer)
            for (i in socketSingles)
                socketSingles[player].sendMsgNewPlayer(i)
                player.sendMsgNewPlayer(player)
            socketSingles.add(newplayers)
        else if (msg = newGame)
            socketAll.close()
            newGame = true
    }

    for (int i = 0; i < nrPlayers; i++)
        socketSinglesThread.start();


    socketSinglesThread:

    while (gameOver)
        awaitMsg
        interpret msg