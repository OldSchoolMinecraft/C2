# C2 (Command & Control)

This plugin, put simply, is a utility/framework plugin that allows *other* plugins to interact with micro-services via a TCP socket.

To get an idea of why this exists, here is a list of existing features & ideas for things you could make with it.
* Link account to external platforms/social media (Discord, Twitter, Reddit, etc)
* Create any interface (web, desktop, mobile)
  * Self-managed auth & permission management
* Create custom packets to trigger actions or transfer data
  * Authenticate (bypass /login)
  * Update/reset password
  * (Admin) Trigger remote server restart
  * (Admin) Send console commands
  * (Admin) Send sudo commands (as another player)
* Anything you can imagine within the limitations & boundaries of the C2 framework.