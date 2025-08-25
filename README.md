# mc-utilities-plugin

mc-utilities-plugin is a lightweight plugin for Minecraft servers that adds a few quality-of-life utilities. It integrates with LuckPerms and provides tools that encourage healthy play habits and configurable player home limits.

## Features
- **MidnightKick**: Automatically removes non-admin players during early morning hours to nudge them toward logging off and getting rest.
- **/upgradehome**: Command for players to spend diamond blocks to increase their maximum number of homes, with permissions managed through LuckPerms.

## Building
mc-utilities-plugin uses Maven. To build the plugin, run:

```
mvn package
```

The compiled JAR will be located in the `target/` directory. Place it in your server's `plugins/` folder to install.

## Configuration
A default configuration file is generated on first run. Use it to toggle features such as `tasks.midnightshutdown` and adjust any other options.

## License
This project is provided under the MIT License.
