import os
from urllib.request import Request, urlopen
from slack_bolt import App
from slack_bolt.adapter.socket_mode import SocketModeHandler

bot_token_file = open("assets/BotKey")
app_token_file = open("assets/AppKey")

# Remember, these 2 tokens are different
SLACK_BOT_TOKEN = bot_token_file.readline()
SLACK_APP_TOKEN = app_token_file.readline()

bot_token_file.close()
app_token_file.close()

# App used to call all Bolt API functions
app = App(token=SLACK_BOT_TOKEN)

# The path to the folder which all images are in
images_directory = "src/main/resources/static/images"

# A list representing all image urls (the private kind which cannot be seen normally)
images = []

# Get image filepath
current_path = os.path.abspath(__file__)
image_folder_path = os.path.join(os.path.dirname(current_path), images_directory)


# Writes the list of images to the images folder, 
# replacing the images once there are more than the maximum allowed
def write_image_to_folder(images):
    if len(images) <= 5:
        '''for file in os.listdir(images_directory): 
            if file.endswith('.jpg'):
                os.remove(file)'''

        for i in range(len(images)):  
            # Adds the images sequentially (0 to length-1)
            # Passes in a header so the code can actually get the image
            # instead of being access denied for not being part of the workspace
            req = Request(images[i])
            req.add_header('Authorization', f'Bearer {SLACK_BOT_TOKEN}')
            content = urlopen(req).read()


            # Writes the file opened from the request in write binary mode (wb) because
            # slack gives the binary data of the file
            with open(f"{images_directory}/image{i}.jpg", "wb") as f:
                f.write(content)
    else:
        # If we are more than the max allowed images, get rid of the first file (at index 0)
        # shift everything else down, and rewrite all the image files
        images.pop(20)
        write_image_to_folder(images)


# Test example message event taken from the Slack Bolt API start guide
@app.message("test")
def message_hello(message, say):
     # say() sends a message to the channel where the event was triggered
    say(
        blocks=[
            {
                "type": "section",
                "text": {"type": "mrkdwn", "text": f"Hey there <@{message['user']}>!"},
                "accessory": {
                    "type": "button",
                    "text": {"type": "plain_text", "text": "Click Me"},
                    "action_id": "button_click"
                }
            }
        ],
        text=f"Hey there <@{message['user']}>!"
    )


# An event which triggers any time a message is sent in a channel
# the bot has access to.
@app.event("message")
def image_grab_action(message, say):
    # Informs the message sender it got the message
    say(f"Message recieved, <@{message['user']}>!")
    print(get_image_url(message))

    # Adds the private image url of the image to the list of image urls
    # in code as long as one is present
    if get_image_url(message) != None:
        images.append(get_image_url(message)) 
        print(message)
        write_image_to_folder(images)


def get_image_url(message):
    # Parses the message "object" and grabs only the private image url element
    # if one is present within the message.  Returns None if one is not present
    if "files" in message:
        if len(message["files"]) > 0:
                if "url_private" in message["files"][0]:
                    if "ts" in message:
                        return message["files"][0]["url_private"]
                    
    return None


# Starts the slack app in socket mode.  Make sure the app is put into socket mode.
SocketModeHandler(app, SLACK_APP_TOKEN).start() 
 
