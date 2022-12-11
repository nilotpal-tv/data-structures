const { readFileSync, writeFileSync } = require('fs');

function initPlayersMapping(players, playersCount) {
  const playersMap = new Map();
  for (let i = 0; i < playersCount; i++) playersMap.set(players[i], 0);
  return playersMap;
}

function getFileData() {
  const fileContents = readFileSync('data.txt', { encoding: 'utf8' }).split(
    '\r\n'
  );
  const noOfPlayers = Number(fileContents[0]);
  const givings = fileContents.slice(noOfPlayers + 1);
  const players = fileContents.slice(1, noOfPlayers + 1);

  return { givings, players, noOfPlayers };
}

function greedyGiftGivers() {
  const { givings, players, noOfPlayers } = getFileData();
  const playersMap = initPlayersMapping(players, noOfPlayers);
  let i = 0;

  while (i < givings.length) {
    const gifter = givings[i];
    const giving = givings[i + 1].split(' ');
    const rupees = Number(giving[0]);
    const noOfRecipients = Number(giving[1]);

    const recipients = givings.slice(i + 2, i + 2 + noOfRecipients);
    const amountToDistribute = Math.floor(rupees / noOfRecipients || 0);
    const remaining = Math.floor(rupees % noOfRecipients || 0) - rupees;

    const currentGifterPrevAmount = Number(playersMap.get(gifter));
    playersMap.set(gifter, remaining + currentGifterPrevAmount);

    for (const recipient of recipients) {
      const currentRecipientPrevAmount = Number(playersMap.get(recipient));
      playersMap.set(
        recipient,
        amountToDistribute + currentRecipientPrevAmount
      );
    }

    i += noOfRecipients + 2;
  }

  let output = '';
  playersMap.forEach((val, key) => (output += `${key} ${val} \n`));
  writeFileSync('output.txt', output);
  console.log(output);
}

greedyGiftGivers();
