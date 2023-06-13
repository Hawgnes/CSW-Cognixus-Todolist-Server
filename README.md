# CSW-Cognixus-Todolist-Server
## Introduction
This is a TODO-list API server where users can:
1. Sign in using Gmail
2. Add a TODO item
3. Delete a TODO item
4. List all TODO items
5. Mark a TODO item as completed

## 0. Instruction for running the app
### 0.1 Running with executable JAR file
Here is an example for running the application with the executable JAR file through the command line; you may replace the credentials and URL according to your local environment's requirements. 

>java -jar Todolist-Server-0.0.1-SNAPSHOT.jar --spring.datasource.url=**jdbc:mysql://localhost:3306/todolistdb** --spring.datasource.username=**DB_USERNAME_HERE** --spring.datasource.password=**DB_PASSWORD_HERE**

This command assumes that you have a MySQL server running on port 3306 and have a database named "todolistdb" - the application will not start if the requirements are not fulfilled. 
### 0.2 Running with Docker container
There is a docker-compose.yml file included in the project, you may start the application by opening navigating to the directory with the docker-compose.yml file and run the following command:

>docker-compose up

Docker will attempt to download the images defined in the docker-compose.yml then run them.
At the moment the datasource and database credentials are hardcoded - you may change the configurations directly in the docker-compose.yml file if you wish. 

## 1. Instruction for testing the app
All API endpoints require the user be authenticated, and currently the application only allow signed in Gmail accounts to call the API endpoints. 
When sending a request to the application, caller is required to include Authorization header of type Bearer Token.
### 1.0 Generating Bearer Token
Keep in mind that bearer tokens expire after one hour, so user may need to repeat the process to obtain another bearer token after it has expired. 
#### 1.0.1 Generate Bearer Token with Postman
In the Authorization tab, select OAuth 2.0 as the Type. 
Fill in the fields under Configure New Token section as below:
| Field Name | Field Value |
| ---------- | ----------- |
| Token Name | Google OAuth2 |
| Callback URL | https://oauth.pstmn.io/v1/browser-callback |
| Auth URL | https://accounts.google.com/o/oauth2/auth |
| Access Token URL | https://oauth2.googleapis.com/token |
| Client ID | 424399540106-pt0e8fvm9vj7e6fsicve7dc25bhcgu1c.apps.googleusercontent.com |
| Client Secret | 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 |
| Scope | openid profile email |

Click the Get New Access Token button, a browser will pop up prompting user to sign in with their Gmail account and grant consent, upon success Postman will show a window with the token. 
![image](https://github.com/Hawgnes/CSW-Cognixus-Todolist-Server/assets/30411458/c449f4d8-b4d9-4ce9-8010-c8a9c21fd828)
Copy and keep the value in id_token - this is the bearer token and it will be used as part of the Authorization header when sending API requests to the endpoints. Alternatively you can keep the value as a variable on Postman, which you can later on refer to their values with syntax like `{{variable_name}}`. 

#### 1.0.2 Generate Bearer Token with this custom batch script
If you for some reason do not wish to use Postman, included in the project is a batch file named get_bearer_token.bat, which you can run by double clicking on the file. Here are the instructions: 
1. Script will open a browser tab and prompt you to login with a Gmail account - upon success you will be shown a success page, please copy the value of the request parameter **code**.
![image](https://github.com/Hawgnes/CSW-Cognixus-Todolist-Server/assets/30411458/a18c30c8-9fc8-49b2-ab0f-3da46a96e1d3)
2. Return to the script window, paste (right click) the value then hit Enter, it will send a curl to obtain the token. Copy the value in **id_token** and keep it, you will need it as part of the Authorization header when sending API request to the endpoints.  If for some reason the curl failed, you may want to try again from step 1 - the code value from step 1 is only valid for a few minutes and can only be used once. 

## 2. Instruction for building the app
In the project root directory (/Todolist-Server), run the following commands to clean and build with maven

>mvn clean
>
>mvn install

It will generate the application .jar file in /target directory.

### 2.1 Building Docker image
Project also includes a Dockerfile which can be used to build a Docker image based on the application's .jar file. 
The following command can be ran at the root directory of the project. 

>docker build -t tag-here .

A Docker image should be created, which can be queried with:

>docker images

## 3. Interface documentation

