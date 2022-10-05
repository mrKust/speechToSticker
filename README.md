# speechToSticker
sentimental telegram bot
## Description
This repository presents a description of a sentimental telegram bot. Based on the received text or voice message, 
this bot analyzes the mood of the sender and sends a picture, gif, quote, poety, video or song in response.
Principle of operation: 
1) The user sends a message to the bot; 
2) The incoming message gets to the server written in python; 
3) Then the data is sent to the deeppavlov library input to analyze the emotionality of this message. As a result, a conclusion is made about the emotional component (positive, negative, neutral); 
4) Then the result is sent to the spring boot server, where the reaction is selected and a specific reaction, the result is sent to the python server; 
5) The result is sent to the chat to the user.

## Functional requirements
- Receiving messages
- Analysis of the mood in the message (in the case of a voice message, transfer it to a text form)
- selecting one of the reaction types and sending the result to the user according to a certain mood

## Architecture
- Architecture with REST
- Object-relational database
- Server-side on Spring boot and Python FastAPI

## Technology stack
- Spring boot
- Java 11
- Hibernate
- Lombok
- MySQL
- Docker
- FastAPI
- Python
- Uvicorn
- DeepPavlov

## Project components interaction
![image](https://user-images.githubusercontent.com/79422421/194120999-21debf77-2345-4b73-859f-7631260b4ec3.png)


## Application functionality demonstration
