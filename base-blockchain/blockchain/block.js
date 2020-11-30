const cryptoHash = require("../utils/cryptoHash")
const hexToBinary = require("hex-to-binary")
class Block {
	constructor({ timestamp, data, lastHash, hash, index }) {
		this.index = index
		this.timestamp = timestamp
		this.data = data
		this.hash = hash
		this.lastHash = lastHash
	}

	static genesis() {
		const GENESIS_DATA = {
			index: 0,
			timestamp: "Genesis timestamp",
			lastHash: "Nil",
			hash: "0000000000000000000000000000000000000000000000000000000000000000",
			data: [],
		}
		return new Block(GENESIS_DATA)
	}

	static addBlock({ lastBlock, data }) {
		const lastHash = lastBlock.hash
		const index = lastBlock.index + 1
		let timestamp, hash
		timestamp = Date.now()
		hash = cryptoHash(index, timestamp, lastHash, data)

		return new Block({
			index,
			timestamp,
			lastHash,
			data,
			hash,
		})
	}
}

module.exports = Block
