const words = require('./words');

(() => {
    const yesterdaysWord = process.argv[2]
    if(!yesterdaysWord) {
        console.error(`You must supply the previous day's word in the process arguments`)
        process.exit()
        return
    }

    const index = words.indexOf(yesterdaysWord)
    console.log(`Today's word is ${words[index+1]}`)
})()