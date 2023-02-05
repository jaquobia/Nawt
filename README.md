## Nawt

A mod made to replace the applet rendering with a framework for any windowing library, with built-in glfw as a default

This mod should work with almost any beta fabric mod since it touches mostly LWJGL classes and a 2 or 3 tiny redirects in Minecraft sources; however its built with STAPI in mind

## Known Issues
Scrolling doesn't work in modded gui's for some non-apparent reason, might have to look into how STAPI retrives the scroll delta.

## Warning

This mod is definitely highly-experimental, so it's a use-at-your-own-risk kind of thing  
Feel free to make issues or pull requests

## Future

I might look into entrypoints for loading new windowing systems, and I could possibly make some bindings for glfw on the path or sdl2

## License

MIT, glhf
