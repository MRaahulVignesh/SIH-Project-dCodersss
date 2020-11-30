const Block = require("./block")
const cryptoHash = require("../utils/cryptoHash")

class Blockchain {
	constructor() {
		this.chain = [Block.genesis()]
	}

	addBlock(data) {
		const NewBlock = Block.addBlock({
			lastBlock: this.chain[this.chain.length - 1],
			data,
		})
		this.chain.push(NewBlock)
	}

	static isValidChain(chain) {
		if (JSON.stringify(chain[0]) !== JSON.stringify(Block.genesis())) return false

		const { index, timestamp, data, lastHash } = chain[i]
		const actualLastHash = chain[i - 1].hash
		if (actualLastHash !== chain[i].lastHash) return false
		const validatedHash = cryptoHash(timestamp, data, lastHash)
		if (validatedHash !== chain[i].hash) return false
		if (chain[i - 1].index + 1 != chain[i].index) return false

		return true
	}

	replaceChain(newChain, onSuccess) {
		if (newChain.length <= this.chain.length) {
			console.error("Entering chain not long enough to replace old chain")
			return
		}

		if (!Blockchain.isValidChain(newChain)) {
			console.error("Entering chain is not valid")
			return
		}

		if (onSuccess) console.log("onSuccess varibale called")
		console.log("Entering chain replaces old chain")
		this.chain = newChain
	}
}

module.exports = Blockchain
