# sentimental telegram bot

## Description
This repository presents a description of a sentimental telegram bot. Based on the received text or voice message, 
this bot analyzes the mood of the sender and sends a picture, gif, quote, poety, video or song in response.
Principle of operation: 
1) The user sends a message to the bot; 
2) The incoming message gets to the server written in python; 
3) Then the data is sent to the EmoRoBERTa library input to analyze the emotionality of this message. As a result, a conclusion is made about the emotional component (positive, negative, neutral); 
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
- EmoRoBERTa

## Project components interaction
![изображение](https://user-images.githubusercontent.com/45081619/201858090-310d5648-d02f-4d5e-8a83-9b10748a1b00.png)<br> *Pic 1.Architecture.* <br>


## Application functionality demonstration
![image](https://user-images.githubusercontent.com/79422421/194142808-844371fb-0372-4db8-b12c-70c065efa4b4.png)<br> *Pic 2. Exmaple with neutral.* <br>
![image](https://user-images.githubusercontent.com/79422421/194143473-20d9edb8-f1af-48a0-bfdf-4499b26d2cc5.png)<br> *Pic 3. Exmaple with positive.* <br>
![image](https://user-images.githubusercontent.com/79422421/194143546-077f0915-4826-4c42-8965-f0ebb10c0d02.png)<br> *Pic 4. Exmaple with negative.* <br>
![image](https://user-images.githubusercontent.com/79422421/194143617-fc471b1b-6152-44e2-be4f-529aefc0f27a.png)<br> *Pic 5. Exmaple with gif.* <br>





