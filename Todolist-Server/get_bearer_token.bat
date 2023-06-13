@echo off
set clientId=424399540106-pt0e8fvm9vj7e6fsicve7dc25bhcgu1c.apps.googleusercontent.com
set clientSecret=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

echo Login with a test email through browser
pause
start "" "https://accounts.google.com/o/oauth2/v2/auth?client_id=%clientId%&redirect_uri=https://oauth.pstmn.io/v1/browser-callback&scope=profile email openid&response_type=code"

set /p "authCode=Key in the request param code's value: "

set "bearerToken="


curl -s --request POST --data "code=%authCode%&client_id=%clientId%&client_secret=%clientSecret%&redirect_uri=https://oauth.pstmn.io/v1/browser-callback&grant_type=authorization_code" "https://accounts.google.com/o/oauth2/token" 
echo 
echo Copy the bearer token from the field "id_token", it is valid for 1 hour

pause