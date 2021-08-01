# 3D-Cube-Web-Browser
Something my friends asked me to make. A 3D cube with 6 WebView on each side. Made with JavaFX.

I hardcoded each WebView as I wanted to finish this quickly. So there is alot of repeated code.

## Notes

Remeber to add JavaFX components to your project.

I'm using IntelliJ Idea.

Go to File ➜ Project Structure ➜ Libraries ➜ + ➜ Java ➜ Add the JavaFX_Path/lib

## Controls

Drag mouse across screen to rotate the cube. Don't click on the cube and then drag - it'll just highlight text on the WebView. Scroll to zoom in/out.

## Changing size

There are 3 static properties, WIDTH, HEIGHT and DEPTH. Change these to change the size of the cube. The size of the WebViews will change accordingly. I placed the WebViews in a StackPane to enable me to change its size.
