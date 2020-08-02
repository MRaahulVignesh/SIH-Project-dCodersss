const PubNub = require('pubnub');
require('dotenv').config()

console.log(process.env.publishKey)
console.log(process.env.subscribeKey)
console.log(process.env.secretKey)
const credentials = {
  publishKey: process.env.publishKey.toString(),
  subscribeKey: process.env.subscribeKey.toString(),
  secretKey: process.env.secretKey.toString()
};

const CHANNELS = {
  BLOCKCHAIN: 'BLOCKCHAIN',
};

class PubSub {
  constructor(blockchain) {
    this.blockchain = blockchain;

    this.pubnub = new PubNub(credentials);

    this.pubnub.subscribe({ channels: [Object.values(CHANNELS)] });

    this.pubnub.addListener(this.listener());
  }

  broadcastChain() {
    this.publish({
      channel: CHANNELS.BLOCKCHAIN,
      message: JSON.stringify(this.blockchain.chain)
    });
  }

  subscribeToChannels() {
    this.pubnub.subscribe({
      channels: [Object.values(CHANNELS)]
    });
  }

  listener() {
    return {
      message: messageObject => {
        const { channel, message } = messageObject;

        console.log(`Message received. Channel: ${channel}. Message: ${message}`);
        const parsedMessage = JSON.parse(message);

        switch(channel) {

          case CHANNELS.BLOCKCHAIN:
            this.blockchain.replaceChain(parsedMessage, true);
            break;
            
          default:
            return;
        }
      }
    }
  }

  publish({ channel, message }) {
    this.pubnub.publish({ message, channel });
  }

 
}

module.exports = PubSub;