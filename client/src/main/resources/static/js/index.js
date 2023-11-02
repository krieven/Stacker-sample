(function () {
    
    let currentScreen;
    const loadedScreens = {};

    function loadScreen(flow, state, data, appContext) {
        if (loadedScreens[flow]) {
            loadScreen.onLoad(loadedScreens[flow], state, data);
            return;
        }
        moduleLoader(appContext,
            'components/' + flow + ".html",
            reader,
            (factory) => {
                loadedScreens[flow] = factory;
                loadScreen.onLoad(factory, state, data);
            });
    }

    loadScreen.onLoad = (factory, state, data) => {
        currentScreen && (currentScreen.stop() || currentScreen.destroy());
        const screen = factory?.create(state + '-screen');
        if (!screen?.mount) {
            console.log('screen ' + state + ' is not loaded');
            return;
        }
        currentScreen = screen;
        screen.mount(document.getElementById('application-root'));
        screen.setData(data);
    }

    function sendAnswer(answer) {
        loadScreen("lib/common", "loading", null, appContext);

        fetch('/api/', { method: 'POST', body: JSON.stringify(answer) })
            .then(resp => resp.json())
            .then(
                question => {
                    loadScreen(question.flow, question.state, question.data, appContext);
                }
            );
    }

    function loadResource(url, callback) {
        fetch('/api' + url)
            .then(resp => resp.json())
            .then(callback);
    }

    const appContext = {
        sendAnswer,
        loadResource
    }

    sendAnswer();
})();