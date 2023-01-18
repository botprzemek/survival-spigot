# bpSurvival plugin

botprzemek's essentials for survival server, compatible with minecraft spigot 1.18+.

commands:
  bpsurvival:
    aliases: [bps]
    default: op
    description: Reload command
  settings:
    aliases: [set]
    default: op
    description: Settings command
  spawn:
    aliases: [s]
    default: op
    description: Spawn command
  vanish:
    aliases: [ukryj, v]
    default: op
    description: Vanish command
  tprequest:
    aliases: [tpa, tpwyslij]
    default: op
    description: Teleport request command
  tpaccept:
    aliases: [tpakceptuj]
    default: op
    description: Teleport accept command
  tpdeny:
    aliases: [tpodrzuc]
    default: op
    description: Teleport deny command
  home:
    aliases: [dom, h]
    default: op
    description: Home command
  sethome:
    aliases: [ustawdom]
    default: op
    description: Set home command
  kit:
    aliases: [zestaw]
    default: op
    description: Kit command
  streaming:
    aliases: [czat]
    default: op
    description: Disables chat command
  message:
    aliases: [msg, wiadomosc]
    default: op
    description: Message command
  reply:
    aliases: [r, odpowiedz]
    default: op
    description: Reply command
permissions:
  bpsurvival.*:
    default: op
    description: Gives access to all bpSurvival commands
  bpsurvival.reload:
    default: op
    description: Reload command permission
  bpsurvival.settings:
    default: op
    description: Settings command permission
  bpsurvival.spawn:
    default: op
    description: Spawn command permission
  bpsurvival.vanish:
    default: op
    description: Vanish command permision
  bpsurvival.tprequest:
    default: op
    description: Teleport request command permission
  bpsurvival.tpaccept:
    default: op
    description: Teleport accept command permission
  bpsurvival.tpdeny:
    default: op
    description: Teleport deny command permission
  bpsurvival.home:
    default: op
    description: Home command permission
  bpsurvival.sethome:
    default: op
    description: Set home command permission
  bpsurvival.homes.0:
    default: op
    description: Homes limit permission
  bpsurvival.kit:
    default: op
    description: Kit command permission
  bpsurvival.chat:
    default: op
    description: Disables chat command permission
  bpsurvival.message:
    default: op
    description: Message command permission
  bpsurvival.reply:
    default: op
    description: Reply command permission
