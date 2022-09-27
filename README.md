name: Notebook application

entities:
-note: a message of length 1-2500 symbols, with dynamic web-content previews
-user: website user

user types:
-user
-admin
-system (localhost only)

user endpoints:
-(POST) login with username & password A
-(POST) login with google (oAuth2) A
-(GET) list of notes A
-(GET) list of pinned notes A
-(GET) list of archived notes A
-(POST) create a note A
-(PUT) pin an existing note A
-(PUT) archive a note A
-(DELETE) delete a note (move to a list of deleted notes) A
-(POST) refresh access token A
-(POST) logout (expire all existing access tokens) -
-(POST) share a note (API) (make available by link) A
-(GET) share a note (webpage) A
-(POST) cancel note sharing -
-(GET) get a shared note, without authorization A
-(GET) settings (webpage) A
-(POST) settings (modify, save in backend) (use cookies for settings) A
-(GET) user settings (json) A 
-(GET) ban page (if banned, after login attempt) -
-(PUT) modify self profile info (excluding credentials) -
-(GET) get self profile info (webpage/json)
note types:
-private
-shared
note status:
-archived
-unarchived
-pinned

user privileges:
-login (if not banned)
-read/write list of private notes by status (self only) (only pinned and unarchived)
-read shared notes by status (unauthorized)
-write shared notes (self or authorized) (only pinned and unarchived)
-create a note (authorized)
-pin a note (authorized)
-archive a note (authorized)
-delete a note (authorized)
-refresh access token (if not banned)
-share a note
-read self settings (if not banned)
-modify self settings (if not banned)
-read/write self profile info (authorized)

admin endpoints
::all user endpoints::
-(GET) user by ID
-(GET) user by name
-(GET) all users by registration date
-(GET) all users by name starting with prefix
-(GET) all users by surname starting with prefix
-(POST) ban a user (specifying a reason)
-(POST) unban a user (specifying a reason)
-(POST) send user a warning message (can be used by system)

system endpoints (only accessible from localhost):
::all admin endpoints::
-(POST) ban/unban an admin
-(POST) permanently delete a user/admin account
-(POST) perform rollback
-(POST) toggle maintenance mode
