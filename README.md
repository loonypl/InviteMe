# InviteMe
## Invite your friends and get prizes. Recommendation plugin for Minecraft 1.12.2
![alt InviteMe](https://i.imgur.com/B4vY0KF.png)
# Download
Mediafire: http://ceesty.com/wgK8RY<br/>
VirusTotal (scan): https://www.virustotal.com/#/file/02bc40500a4a8dcba7aed6ab071da629735f1bee5819d738f46298c3bbaed240/detection
#
Permissions:
* inviteme.default - gives default access (recommended for non-admin players)
* inviteme.* - gives "super" access (recommended for admin players)
#
Commands (* - required):
* /inviteme - plugin commands (inviteme.default)
* /inviteme recommend [player*] - recommend player (inviteme.default)
* /inviteme stats [player] - check statistics (inviteme.default)
* /inviteme prizes - check available prizes (inviteme.default)
* /inviteme setinvites [player*] [number*] - set player's invites (inviteme.*)
* /inviteme setpoints [player*] [number*] - set player's points (inviteme.*)
* /inviteme reload - reload configuration (inviteme.*)
#
Configuration file (/InviteMe/config.yml):
```
# © krzysek (git: loonypl)
# InviteMe configuration file
#
# Permissions:
#  - inviteme.default - default access (for players)
#  - inviteme.*       - super access   (for admins)
#
# MySQL - Configuration
#
mysql:
  user: 'root'
  password: '123'
  host: '127.0.0.1'
  port: '3306'
  database: 'inviteme'
#
# Recommendations - Configuration
#
recommendations:
  # points per recommendation
  points: 10
#
# Prizes - Configuration
#
prizes:
  # gui configuration
  gui:
    # gui name
    # %player%   - player's name
    # %points%   - player's points
    # %invites%  - player's invites
    name: '&8%player% &e| &8Points: &e%points%'
    # texts
    texts:
      name: 'Name'
      description: 'Description'
      price: 'Price (points)'
    # gui size
    size: 27
    # active prizes (split prize-IDs by ';')
    active: 'prize1;prize2'
    # prizes set
    set:
      # first prize
      prize1:
        # prize position in gui
        position: 12
        # prize item (in gui)
        item: 'GRASS'
        # prize name
        name: '&eGamemode 1'
        # prize description
        description: '&eChange your gamemode to creative ! :D'
        # prize price (in points)
        price: 100
        # prize recieve message
        message: '&aIts nice to hear you invite friends ! :)'
        # commands
        commands:
          - 'gamemode 1 %player%'
      # second prize
      prize2:
        # prize position in gui
        position: 14
        # prize item (in gui)
        item: 'DIAMOND'
        # prize name
        name: '&e2 Diamonds'
        # prize description
        description: '&eRecieve 2 diamonds :)'
        # prize price (in points)
        price: 10
        # prize recieve message
        message: '&aOoo... its shining... :O'
        # commands
        commands:
          - 'give %player% diamond 2'

#
# Messages - Configuration
#
messages:
  # incorrect usage
  usage: '&cIncorrect usage. Get more info at &f/inviteme'
  # no access
  no-access: '&cYou do not have permission to this !'
  # player not found
  player-not-found: '&cPlayer is not online ! :('
  # player not found in database
  player-not-found-db: '&cPlayer not found in database'
  # not numeric
  not-numeric: '&cGiven argument is not numeric. Get more info at &f/inviteme'
  # invites set
  invites-set: '&aInvites of &6%player% &ahas been set to &6%amount% &7!'
  # invites set
  points-set: '&aPoints of &6%player% &ahas been set to &6%amount% &7!'
  # already recommended
  already-recommended: '&cYou have already recommended the player !'
  # successfully recommended
  success-recommended: '&aYou have recommended &6%player% &a! :)'
  # successfully recommended (message to recommended player)
  success-recommended-recieved: '&aYou have recieved points by recommending &6%player% &a! Nice :)'
  # unsuccessfully recommended
  fail-recommended: '&cYou cannot recommended yourself ! :('
  # not enough points
  not-enough-points: '&cYou do not have enough points ! :('
  # configuration reloaded
  config-reloaded: '&aConfiguration reloaded !'
  # message sent to player on join
  on-join:
    enable: true
    message: '&7Help our server - invite friends and get prizes ! :) More at &f/inviteme'
  # /inviteme stats
  stats-command:
    nickname: 'Nickname'
    invites: 'Invites'
    points: 'Points'
    recommended: 'Recommended'
    recommended-yes: 'Yes'
    recommended-no: 'No'
```
#
Have any new ideas ? Share it at https://github.com/loonypl/InviteMe/issues
