# Build and Run ReadCore

echo "Building ReadCore..."
mvn clean package

if [ $? -eq 0 ]; then
    echo ""
    echo "Build successful! Running ReadCore..."
    echo ""
    java -jar target/readcore-1.0.0.jar
else
    echo ""
    echo "Build failed. Please check the errors above."
fi
