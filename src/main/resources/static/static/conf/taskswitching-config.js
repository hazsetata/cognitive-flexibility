if (!ts) {
    alert("TASKSWITHING-CORE not properly loaded.");
}

// config for the app goes here
ts.config = {};

ts.config.endText = "Thank you for participating!";

// backend to submit data to
ts.config.backendResultAddress = "app/result";

// backend to get list counts from
ts.config.backendListCountAddress = "app/listcount";


// backend to get list counts from
ts.config.backendListCountAddress = "app/listcount";

// how long to hide an element in milliseconds
ts.config.pauseBeforeFirstShow = 2500;

ts.config.pauseAfterWrongAnswerInMs = 1500;
ts.config.pauseAfterCorrectAnswerInMs = 150;

// how long to show an element in milliseconds
ts.config.elementVisibleInMs = 2500;

// pause between tests
ts.config.pauseBetweenTests = 5000;

// pause between tests
ts.config.maxTrainingIterations = 3;

ENGLISH_TEXTS = {
    REACTION_TEST_START_TEXT: "Reaction time task<br/><br/>Press 'x', when you see any number-letter -pair on the screen.<br/><br/>Do the task as QUICKLY as you can.<br/><br/>Press spacebar to start.",
    REACTION_TEST_END_TEXT: "Awesome! RT is your average reaction time.",
    NUMBERTASK_PRACTICE_START_TEXT: "NUMBER task practise: Please observe the top row.<br/><br/>When the number is odd, press 'x'. When the number is even, press 'm'.<br/>Do the task as QUICKLY and CORRECTLY as possible.<br/><br/>Press spacebar to start to practise.",
    NUMBERTASK_PRACTICE_END_TEXT: "TODO: TEXT",
    NUMBERTASK_PRACTICE_ATTEMPTS_LEFT: "Great work!<br/>Press 'x' to continue straight to the task.<br/>Remember: When the number is odd, press 'x'. When the number is even, press 'm'.<br/><br/>If you want, you can still continue to practise by pressing spacebar.",
    NUMBERTASK_START_TEXT: "Remember: When the number is odd, press 'x'. When the number is even, press 'm'.<br/><br/>Press spacebar to start.",
    NUMBERTASK_END_TEXT: "TODO: TEXT",
    CHARACTERTASK_PRACTICE_START_TEXT: "LETTER task practise: Please observe the bottom row.<br/><br/>When the letter is a consonant, press 'x'. When the letter is a vowel, press 'm'.<br/>Do the task as QUICKLY and CORRECTLY as possible.<br/><br/>Press spacebar to start to practise.",
    CHARACTERTASK_PRACTICE_END_TEXT: "TODO: TEXT",
    CHARACTERTASK_PRACTICE_ATTEMPTS_LEFT: "Great work!<br/>Press 'x' to continue straight to the task.<br/>Remember: When the letter is a consonant, press 'x'. When the letter is a vowel, press 'm'.<br/><br/>If you want, you can still continue to practise by pressing spacebar.",
    CHARACTERTASK_START_TEXT: "Remember: When the letter is a consonant, press 'x'. When the letter is a vowel, press 'm'.<br/><br/>Press spacebar to start.",
    CHARACTERTASK_END_TEXT: "TODO: TEXT",
    TASKSWITCHING_PRACTICE_START_TEXT: "Let's combine the previous number- and letter tasks: Please observe both rows.<br><br/>First, you get to practise the task.<br/><br/>Top row: When the number is odd, press 'x'. When the number is even, press 'm'.<br/>Bottom row: When the letter is a consonant, press 'x'. When the letter is a vowel, press 'm'.<br/><br/>Do the task as QUICKLY and CORRECTLY as possible.<br/><br/>Press spacebar to start to practise.",
    TASKSWITCHING_PRACTICE_END_TEXT: "TODO: TEXT",
    TASKSWITCHING_PRACTICE_ATTEMPTS_LEFT: "Great work!<br/>Press 'x' to continue straight to the task.<br/><br/>Remember:<br/>On the top row, odd = 'x', even = 'm'.<br/>On the bottom row, consonant = 'x', vowel = 'm'.<br><br/>If you want, you can still continue to practise by pressing spacebar.",
    TASKSWITCHING_START_TEXT: "Remember:<br/>On the top row, odd = 'x', even = 'm'.<br/>On the bottom row, consonant = 'x', vowel = 'm'.<br/><br/>Press spacebar to start.",
    TASKSWITCHING_END_TEXT: "Thank you for participating!"
};

ENGLISH_TEXTS_MOBILE = {
    REACTION_TEST_START_TEXT: "Reaction time task<br/><br/>Press red, when you see any number-letter -pair on the screen.<br/><br/>Do the task as QUICKLY as you can.<br/><br/>Press black to start.",
    REACTION_TEST_END_TEXT: "Awesome! RT is your average reaction time.",
    NUMBERTASK_PRACTICE_START_TEXT: "NUMBER task practise: Please observe the top row.<br/><br/>When the number is odd, press red. When the number is even, press green.<br/>Do the task as QUICKLY and CORRECTLY as possible.<br/><br/>Press black to start to practise.",
    NUMBERTASK_PRACTICE_END_TEXT: "TODO: TEXT",
    NUMBERTASK_PRACTICE_ATTEMPTS_LEFT: "Great work!<br/>Press red to continue straight to the task.<br/>Remember: When the number is odd, press red. When the number is even, press green.<br/><br/>If you want, you can still continue to practise by pressing black.",
    NUMBERTASK_START_TEXT: "Remember: When the number is odd, press red. When the number is even, press green.<br/><br/>Press black to start.",
    NUMBERTASK_END_TEXT: "TODO: TEXT",
    CHARACTERTASK_PRACTICE_START_TEXT: "LETTER task practise: Please observe the bottom row.<br/><br/>When the letter is a consonant, press red. When the letter is a vowel, press green.<br/>Do the task as QUICKLY and CORRECTLY as possible.<br/><br/>Press black to start to practise.",
    CHARACTERTASK_PRACTICE_END_TEXT: "TODO: TEXT",
    CHARACTERTASK_PRACTICE_ATTEMPTS_LEFT: "Great work!<br/>Press red to continue straight to the task.<br/>Remember: When the letter is a consonant, press red. When the letter is a vowel, press green.<br/><br/>If you want, you can still continue to practise by pressing black.",
    CHARACTERTASK_START_TEXT: "Remember: When the letter is a consonant, press red. When the letter is a vowel, press green.<br/><br/>Press black to start.",
    CHARACTERTASK_END_TEXT: "TODO: TEXT",
    TASKSWITCHING_PRACTICE_START_TEXT: "Let's combine the previous number- and letter tasks: Please observe both rows.<br><br/>First, you get to practise the task.<br/><br/>Top row: When the number is odd, press red. When the number is even, press green.<br/>Bottom row: When the letter is a consonant, press red. When the letter is a vowel, press green.<br/><br/>Do the task as QUICKLY and CORRECTLY as possible.<br/><br/>Press black to start to practise.",
    TASKSWITCHING_PRACTICE_END_TEXT: "TODO: TEXT",
    TASKSWITCHING_PRACTICE_ATTEMPTS_LEFT: "Great work!<br/>Press red to continue straight to the task.<br/><br/>Remember:<br/>On the top row, odd = red, even = green.<br/>On the bottom row, consonant = red, vowel = green.<br><br/>If you want, you can still continue to practise by pressing black.",
    TASKSWITCHING_START_TEXT: "Remember:<br/>On the top row, odd = red, even = green.<br/>On the bottom row, consonant = red, vowel = green.<br/><br/>Press black to start.",
    TASKSWITCHING_END_TEXT: "Thank you for participating!"
};

FINNISH_TEXTS = {
    REACTION_TEST_START_TEXT: "Reaktioaikatehtävä<br/><br/>Paina 'x' kun näet minkä tahansa numero-kirjainparin ruudulla. <br/><br/>Tee tehtävä NIIN NOPEASTI kuin pystyt.<br/><br/>Paina välilyöntiä jatkaaksesi.",
    REACTION_TEST_END_TEXT: "Mainiota! Keskimääräinen reaktioaikasi on RT.",
    NUMBERTASK_PRACTICE_START_TEXT: "NUMEROTEHTÄVÄN harjoitus: Tarkkaile yläriviä.<br/><br/>Jos numero on pariton, paina 'x'. Jos numero on parillinen, paina 'm'.<br/>Tee tehtävä niin NOPEASTI ja TARKASTI kuin pystyt.<br/><br/>Paina välilyöntiä aloittaaksesi harjoittelun.",
    NUMBERTASK_PRACTICE_END_TEXT: "TODO: TEXT",
    NUMBERTASK_PRACTICE_ATTEMPTS_LEFT: "Hyvä!<br/>Paina 'x' jatkaaksesi varsinaiseen tehtävään.<br/>Muista: Kun numero on pariton, paina 'x'. Kun numero on parillinen, paina 'm'.<br/><br/>Jos haluat vielä harjoitella ennen tehtävän suorittamista, paina välilyöntiä.",
    NUMBERTASK_START_TEXT: "Muista: Kun numero on pariton, paina 'x'. Kun numero on parillinen, paina 'm'.<br/><br/>Paina välilyöntiä aloittaaksesi.",
    NUMBERTASK_END_TEXT: "TODO: TEXT",
    CHARACTERTASK_PRACTICE_START_TEXT: "KIRJAINTEHTÄVÄN harjoittelu: Tarkkaile alariviä.<br/><br/>Jos kirjain on konsonantti, paina 'x'. Jos kirjain on vokaali, paina 'm'.<br/>Tee tehtävä niin NOPEASTI ja TARKASTI kuin pystyt.<br/><br/>Paina välilyöntiä aloittaaksesi harjoittelun.",
    CHARACTERTASK_PRACTICE_END_TEXT: "TODO: TEXT",
    CHARACTERTASK_PRACTICE_ATTEMPTS_LEFT: "Hyvää työtä!<br/>Paina 'x' jatkaaksesi varsinaiseen tehtävään.<br/>Muista: Jos kirjain on konsonantti, paina 'x'. Jos kirjain on vokaali, paina 'm'.<br/><br/>Jos haluat vielä harjoitella ennen tehtävän suorittamista, paina välilyöntiä.",
    CHARACTERTASK_START_TEXT: "Muista: Jos kirjain on konsonantti, paina 'x'. Jos kirjain on vokaali, paina 'm'.<br/><br/>Paina välilyöntiä aloittaaksesi.",
    CHARACTERTASK_END_TEXT: "TODO: TEXT",
    TASKSWITCHING_PRACTICE_START_TEXT: "Nyt tehdään numero- ja kirjaintehtävää samanaikaisesti: Huomioi sekä ylä- että alarivi.<br><br/>Saat ensin harjoitella.<br/><br/>Ylärivillä: Jos numero on pariton, paina 'x'. Jos numero on parillinen, paina 'm'.<br/>Alarivilla: Jos kirjain on konsonantti, paina 'x'. Jos kirjain on vokaali, paina 'm'.<br/><br/>Tee tehtävä niin NOPEASTI ja TARKASTI kuin pystyt.<br/><br/>Paina välilyöntiä aloittaaksesi harjoittelun.",
    TASKSWITCHING_PRACTICE_END_TEXT: "TODO: TEXT",
    TASKSWITCHING_PRACTICE_ATTEMPTS_LEFT: "Hienoa!<br/>Paina 'x' jatkaaksesi varsinaiseen tehtävään.<br/><br/>Muista:<br/>Ylärivillä: pariton = 'x', parillinen = 'm'.<br/>Alarivilla: konsonantti = 'x', vokaali = 'm'.<br><br/>Jos haluat vielä harjoitella ennen tehtävän suorittamista, paina välilyöntiä.",
    TASKSWITCHING_START_TEXT: "Muista:<br/>Ylärivillä: pariton = 'x', parillinen = 'm'.<br/>Alarivilla: konsonantti = 'x', vokaali = 'm'.<br/><br/>Paina välilyöntiä aloittaaksesi.",
    TASKSWITCHING_END_TEXT: "Kiitos osallistumisesta!"
};

FINNISH_TEXTS_MOBILE = {
    REACTION_TEST_START_TEXT: "Reaktioaikatehtävä<br/><br/>Paina punaista painiketta kun näet minkä tahansa numero-kirjainparin ruudulla. <br/><br/>Tee tehtävä NIIN NOPEASTI kuin pystyt.<br/><br/>Paina mustaa painiketta jatkaaksesi.",
    REACTION_TEST_END_TEXT: "Mainiota! Keskimääräinen reaktioaikasi on RT.",
    NUMBERTASK_PRACTICE_START_TEXT: "NUMEROTEHTÄVÄN harjoitus: Tarkkaile yläriviä.<br/><br/>Jos numero on pariton, paina punaista painiketta. Jos numero on parillinen, paina vihreää painiketta.<br/>Tee tehtävä niin NOPEASTI ja TARKASTI kuin pystyt.<br/><br/>Paina mustaa painiketta aloittaaksesi harjoittelun.",
    NUMBERTASK_PRACTICE_END_TEXT: "TODO: TEXT",
    NUMBERTASK_PRACTICE_ATTEMPTS_LEFT: "Hyvä!<br/>Paina punaista painiketta jatkaaksesi varsinaiseen tehtävään.<br/>Muista: Kun numero on pariton, paina punaista painiketta. Kun numero on parillinen, paina vihreää painiketta.<br/><br/>Jos haluat vielä harjoitella ennen tehtävän suorittamista, paina mustaa painiketta.",
    NUMBERTASK_START_TEXT: "Muista: Kun numero on pariton, paina punaista painiketta. Kun numero on parillinen, paina vihreää painiketta.<br/><br/>Paina mustaa painiketta aloittaaksesi.",
    NUMBERTASK_END_TEXT: "TODO: TEXT",
    CHARACTERTASK_PRACTICE_START_TEXT: "KIRJAINTEHTÄVÄN harjoittelu: Tarkkaile alariviä.<br/><br/>Jos kirjain on konsonantti, paina punaista painiketta. Jos kirjain on vokaali, paina vihreää painiketta.<br/>Tee tehtävä niin NOPEASTI ja TARKASTI kuin pystyt.<br/><br/>Paina mustaa painiketta aloittaaksesi harjoittelun.",
    CHARACTERTASK_PRACTICE_END_TEXT: "TODO: TEXT",
    CHARACTERTASK_PRACTICE_ATTEMPTS_LEFT: "Hyvää työtä!<br/>Paina mustaa painiketta jatkaaksesi varsinaiseen tehtävään.<br/>Muista: Jos kirjain on konsonantti, paina punaista painiketta. Jos kirjain on vokaali, paina vihreää painiketta.<br/><br/>Jos haluat vielä harjoitella ennen tehtävän suorittamista, paina mustaa painiketta.",
    CHARACTERTASK_START_TEXT: "Muista: Jos kirjain on konsonantti, paina punaista painiketta. Jos kirjain on vokaali, paina vihreää painiketta.<br/><br/>Paina mustaa painiketta aloittaaksesi.",
    CHARACTERTASK_END_TEXT: "TODO: TEXT",
    TASKSWITCHING_PRACTICE_START_TEXT: "Nyt tehdään numero- ja kirjaintehtävää samanaikaisesti: Huomioi sekä ylä- että alarivi.<br><br/>Saat ensin harjoitella.<br/><br/>Ylärivillä: Jos numero on pariton, paina punaista painiketta. Jos numero on parillinen, paina vihreää painiketta.<br/>Alarivilla: Jos kirjain on konsonantti, paina punaista painiketta. Jos kirjain on vokaali, paina vihreää painiketta.<br/><br/>Tee tehtävä niin NOPEASTI ja TARKASTI kuin pystyt.<br/><br/>Paina mustaa painiketta aloittaaksesi harjoittelun.",
    TASKSWITCHING_PRACTICE_END_TEXT: "TODO: TEXT",
    TASKSWITCHING_PRACTICE_ATTEMPTS_LEFT: "Hienoa!<br/>Paina punaista painiketta jatkaaksesi varsinaiseen tehtävään.<br/><br/>Muista:<br/>Ylärivillä: pariton = punainen, parillinen = vihreä.<br/>Alarivilla: konsonantti = punainen, vokaali = vihreä.<br><br/>Jos haluat vielä harjoitella ennen tehtävän suorittamista, paina mustaa painiketta.",
    TASKSWITCHING_START_TEXT: "Muista:<br/>Ylärivillä: pariton = punainen, parillinen = vihreä.<br/>Alarivilla: konsonantti = punainen, vokaali = vihreä.<br/><br/>Paina mustaa painiketta aloittaaksesi.",
    TASKSWITCHING_END_TEXT: "Kiitos osallistumisesta!"
};

// set used text
ts.texts = ($.isMobileMode()) ? FINNISH_TEXTS_MOBILE : FINNISH_TEXTS;
if ($.getLanguageCode() === 'en') {
    ts.texts = ($.isMobileMode()) ? ENGLISH_TEXTS_MOBILE : ENGLISH_TEXTS;
}

ts.pracelements = {
    NUMBER_TEST_ELEMENTS: [[
            {
                text: "g8",
                location: TOP,
                align: FAR_RIGHT,
                correctAnswer: "RIGHT"
            },
            {
                text: "e7",
                location: TOP,
                align: NEAR_RIGHT,
                correctAnswer: "LEFT"
            },
            {
                text: "i5",
                location: TOP,
                align: MIDDLE,
                correctAnswer: "LEFT"
            },
            {
                text: "m4",
                location: TOP,
                align: FAR_LEFT,
                correctAnswer: "RIGHT"
            },
            {
                text: "a2",
                location: TOP,
                align: NEAR_LEFT,
                correctAnswer: "RIGHT"
            },
            {
                text: "u9",
                location: TOP,
                align: MIDDLE,
                correctAnswer: "LEFT"
            }

        ]],
    CHARACTER_TEST_ELEMENTS: [[
            {
                text: "u6",
                location: BOTTOM,
                align: FAR_RIGHT,
                correctAnswer: "RIGHT"
            },
            {
                text: "i5",
                location: BOTTOM,
                align: NEAR_RIGHT,
                correctAnswer: "RIGHT"
            },
            {
                text: "m8",
                location: BOTTOM,
                align: MIDDLE,
                correctAnswer: "LEFT"
            },
            {
                text: "a4",
                location: BOTTOM,
                align: FAR_LEFT,
                correctAnswer: "RIGHT"
            },
            {
                text: "r7",
                location: BOTTOM,
                align: NEAR_LEFT,
                correctAnswer: "LEFT"
            },
            {
                text: "g2",
                location: BOTTOM,
                align: MIDDLE,
                correctAnswer: "LEFT"
            }
        ]],
    TASK_SWITCHING_ELEMENTS: [[
            {
                text: "k8",
                location: BOTTOM,
                align: FAR_RIGHT,
                correctAnswer: "LEFT"
            },
            {
                text: "r3",
                location: TOP,
                align: NEAR_RIGHT,
                correctAnswer: "LEFT"
            },
            {
                text: "m8",
                location: TOP,
                align: MIDDLE,
                correctAnswer: "RIGHT"
            },
            {
                text: "r9",
                location: BOTTOM,
                align: FAR_LEFT,
                correctAnswer: "LEFT"
            },
            {
                text: "m2",
                location: BOTTOM,
                align: NEAR_LEFT,
                correctAnswer: "LEFT"
            },
            {
                text: "a7",
                location: TOP,
                align: MIDDLE,
                correctAnswer: "LEFT"
            }
        ]]

};

ts.config.loadPracticeTest = function(type, initialDescription, postText) {
    var elements;
    switch (type) {
        case "NUMBERREACTION":
            elements = ts.pracelements.NUMBER_TEST_ELEMENTS;
            break;
        case "CHARACTERREACTION":
            elements = ts.pracelements.CHARACTER_TEST_ELEMENTS;
            break;
        case "TASKSWITCHING":
            elements = ts.pracelements.TASK_SWITCHING_ELEMENTS;
            break;
        default:
            console.log("Unable to determine test to load!: " + type);
    }


    var test = new ts.fn.createTest(
            type,
            initialDescription,
            postText,
            elements);

    ts.tests = [];
    ts.tests.push(test);
};

ts.config.loadTestSet = function(type, initialDescription, postText, dataUrl) {
    const guideElement = $("#guide")

    if ($.getLanguageCode() === 'en') {
        guideElement.html($("#moment-english").text());
    }
    else {
        guideElement.html($("#moment-finnish").text());
    }
    guideElement.show();

    let elements = null;
    $.ajax({
        url: dataUrl,
        async: false,
        success: function(json) {
            elements = json;
        },
        dataType: "json"
    });

    guideElement.hide();
    let test = new ts.fn.createTest(
            type,
            initialDescription,
            postText,
            elements);

    ts.tests = [];
    ts.tests.push(test);
};