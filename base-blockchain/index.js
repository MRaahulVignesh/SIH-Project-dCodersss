const express = require('express');
const request = require('request');
const Blockchain = require('./Blockchain/blockchain');
const PubSub = require('./App/pubsub');
 
const DEFAULT_PORT = 3000;
const ROOT_NODE_ADDRESS = `http://localhost:${DEFAULT_PORT}`;

const app = express();

const blockchain = new Blockchain();
const pubsub = new PubSub(blockchain);

app.use(express.json());

app.get('/api/blocks', (req, res) => {
  console.log(JSON.stringify(blockchain.chain,null,'\t'));
  res.json(blockchain.chain);

});
app.get('/api/blockIndex/:id', function(req, res) {
  res.send(blockchain.chain[req.params.id]);    
});
app.post('/api/addBlock', (req, res) => {
  const {data} = req.body;

  blockchain.addBlock(data);
  pubsub.broadcastChain();

  res.redirect('/api/blocks');
});


const syncWithRootState = () => {
  
  request({ url: `${ROOT_NODE_ADDRESS}/api/blocks` }, (error, response, body) => {
    if (!error && response.statusCode === 200) {
      const rootChain = JSON.parse(body);

      console.log('replace chain on a sync with', rootChain);
      blockchain.replaceChain(rootChain);
    }
  });
}
  
let PEER_PORT;
/*
if (process.env.GENERATE_PEER_PORT === 'true') {
  PEER_PORT = DEFAULT_PORT + Math.ceil(Math.random() * 1000);
}
*/
const PORT = process.env.PEER_PORT || DEFAULT_PORT;
app.listen(PORT, () => {
  console.log(`listening at localhost:${PORT}`);

  if (PORT !== DEFAULT_PORT) {
    syncWithRootState();
  }
});

