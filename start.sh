export $(cat .env | xargs)
./gradlew bootRun