# Message Manager
Message manager is used to develop plugins faster to PaperMC based servers.
Servers without the AdventureAPI implemented natively will not be supported
as using BukkitAudiences does not fit this project.
It uses components and allows using placeholders.
The message manager takes inputs like `message.key.one` or `message.key.server` and allows server owners
to define the values for the message in a custom configuration file method.

## Placeholders
Placeholders can be defined globally by the API users or by the server owners who use the message manager.
It has a section in each message to define a placeholder (or however many you need).
Plugin developers can define their own placeholders globally and locally too per message.
Plugin developers can use ``new Placeholder(String, Value)``
and ``new LegacyPlaceholder(String, String)`` to create placeholders.

*(Other placeholder plugins are not supported currently, but adding support for other placeholder plugins would be ideal)*

## Configuration Layout
