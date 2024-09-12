# Cogwheel
![Status](https://img.shields.io/badge/Status-In%20Development-orange?style=flat)
![Discord API Version](https://img.shields.io/badge/Discord%20API%20Version-v10-green?style=flat)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/dc99d8c35fe94c87a427a07499135cd0)](https://app.codacy.com/gh/dark-comet/Cogwheel/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

A Discord API library for JVM-based languages, built in Kotlin. The project consists of two layers: 
- `cogwheel-core` is the low-level library containing Kotlin language bindings for the Discord API.
- `cogwheel-dsl` builds on top of `cogwheel-core`, providing a set of opinionated, high level models 
  to implement bot functionalities with.

Refer to the documentation in `docs/*` and/or the projects in `examples` folder to get started.

## Project Status
### HTTP Resources Support
[Reference Link](https://discord.com/developers/docs/resources/application)

| Resource              | Status |
|-----------------------|--------|
| Application           | -      |
| APC Metadata          | -      |
| Audit Log             | -      |
| AutoMod               | -      |
| Channel               | -      |
| Emoji                 | -      |
| Entitlement           | -      |
| Guild                 | -      |
| Guild Scheduled Event | -      |
| Guild Template        | -      |
| Invite                | -      |
| Message               | -      |
| Poll                  | -      |
| SKU                   | -      |
| Stage Instance        | -      |
| Sticker               | -      |
| Subscription          | -      |
| User                  | -      |
| Voice                 | -      |
| Webhook               | -      |

### Gateway Event Support

[Reference link](https://discord.com/developers/docs/topics/gateway-events)

**Sent Events**

| Resource              | Status |
|-----------------------|--------|
| Identify              | -      |
| Resume                | -      |
| Heartbeat             | -      |
| Request Guild Members | -      |
| Update Voice State    | -      |
| Update Presence       | -      |

**Received Events**

| Resource                               | Status |
|----------------------------------------|--------|
| Hello                                  | -      |
| Ready                                  | -      |
| Resumed                                | -      |
| Reconnect                              | -      |
| Invalid Session                        | -      |
| Application Command Permissions Update | -      |
| AutoMod Rule Create                    | -      |
| AutoMod Rule Update                    | -      |
| AutoMod Rule Delete                    | -      |
| AutoMod Action Execution               | -      |
| Channel Create                         | -      |
| Channel Update                         | -      |
| Channel Delete                         | -      |
| Channel Pins Update                    | -      |
| Thread Create                          | -      |
| Thread Update                          | -      |
| Thread Delete                          | -      |
| Thread List Sync                       | -      |
| Thread Member Update                   | -      |
| Thread Members Update                  | -      |
| Entitlement Create                     | -      |
| Entitlement Update                     | -      |
| Entitlement Delete                     | -      |
| Guild Create                           | -      |
| Guild Update                           | -      |
| Guild Delete                           | -      |
| Guild Audit Log Entry Create           | -      |
| Guild Ban Add                          | -      |
| Guild Ban Remove                       | -      |
| Guild Emojis Update                    | -      |
| Guild Stickers Update                  | -      |
| Guild Integrations Update              | -      |
| Guild Member Add                       | -      |
| Guild Member Remove                    | -      |
| Guild Member Update                    | -      |
| Guild Member Chunk                     | -      |
| Guild Role Create                      | -      |
| Guild Role Update                      | -      |
| Guild Role Delete                      | -      |
| Guild Scheduled Event Create           | -      |
| Guild Scheduled Event Update           | -      |
| Guild Scheduled Event Delete           | -      |
| Guild Scheduled Event User Add         | -      |
| Guild Scheduled Event User Remove      | -      |
| Integration Create                     | -      |
| Integration Update                     | -      |
| Integration Delete                     | -      |
| Integration Create                     | -      |
| Invite Create                          | -      |
| Invite Delete                          | -      |
| Message Create                         | -      |
| Message Update                         | -      |
| Message Delete                         | -      |
| Message Delete Bulk                    | -      |
| Message Reaction Add                   | -      |
| Message Reaction Remove                | -      |
| Message Reaction Remove All            | -      |
| Message Reaction Remove Emoji          | -      |
| Presence Update                        | -      |
| Stage Instance Create                  | -      |
| Stage Instance Update                  | -      |
| Stage Instance Delete                  | -      |
| Subscription Create                    | -      |
| Subscription Update                    | -      |
| Subscription Delete                    | -      |
| Typing Start                           | -      |
| User Update                            | -      |
| Voice Channel Effect Send              | -      |
| Voice State Update                     | -      |
| Voice Server Update                    | -      |
| Webhooks Update                        | -      |
| Message Poll Vote Add                  | -      |
| Message Poll Vote Remove               | -      |