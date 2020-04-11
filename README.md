# Bluzelle Chat App

This project using: [shadow-cljs](https://github.com/thheller/shadow-cljs), [React Native](https://facebook.github.io/react-native/), [Expo](https://expo.io/), [Reagent](https://reagent-project.github.io/), and [re-frame](https://github.com/Day8/re-frame).

<img src="./rn-rf-shadow.png" width="320" />

## Development
```sh
$ npm install -g expo-cli
$ yarn
$ shadow-cljs watch app
# wait for first compile to finish or expo gets confused 
# on another terminal tab/window:
$ yarn start
```
This will run Expo DevTools at http://localhost:19002/

To run the app in browser using expo-web (react-native-web), press `w` in the same terminal after expo devtools is started.
This should open the app automatically on your browser after the web version is built. If it does not open automatically, open http://localhost:19006/ manually on your browser.

Note that you can also run the following instead of `yarn start` to run the app in browser:
   ```
   # same as expo start --web
   $ yarn web
   
   # or
   
   # same as expo start --web-only
   $ yarn web-only
   ```
Then use your editor of choice to hook up the REPL and such.


### Using ClojureScript REPL
Once the app is deployed and opened in phone/simulator/emulator/browser, connect to the nrepl on port 9000 and run the following:
```clojure
(shadow/nrepl-select :app)
```

```clojure
(js/alert "Hello from Repl")
```

## Production builds

A production build invloves first asking shadow-cljs to build a relase, then to ask Expo to work in Production Mode.

**NB**: Currently there's a [bug in the metro bundler](https://github.com/facebook/metro/issues/291) that causes release builds to fail in Production Mode. This project includes a way to patch it (nicked from [here](https://github.com/drapanjanas/re-natal/issues/203)). Patch by executing: `patch node_modules/metro/src/JSTransformer/worker.js ./etc/metro-bundler.patch`

1. Kill the watch and expo tasks.
1. Execute `shadow-cljs release app`
1. Start the expo task (as per above)
   1. Enable Production mode.
   1. Start the app.

If you get complaints about [Module HMRClient is not a registered callable module](https://github.com/expo/expo/issues/916)*, you probably have **Hot reloading** enabled. Disable it and try again.

## Toolchain configurations

Expo SDK          | 35                | 35
----------------- | ----------------- | -----------------
clojure           | 1.10.1            | 1.10.1
clojurescript     | 1.10.520          | **1.10.597**
expo-cli          | 3.4.1             | **3.9.1**
expo              | 35.0.0            | 35.0.0
jdk               | openjdk 1.8.0_222 | openjdk 1.8.0_222
node              | 10.17.0           | 10.17.0
re-frame          | 0.11.0-rc2        | **0.11.0-rc3**
react             | 16.9.0            | 16.9.0
react-native      | 0.59.8            | 0.59.8
reagent           | 0.9.0-rc2         | **0.9.0-rc3**
shadow-cljs (cli) | 2.8.69            | **2.8.78**
shadow-cljs (jar) | 2.8.69            | **2.8.78**
yarn              | 1.19.1            | 1.19.1
