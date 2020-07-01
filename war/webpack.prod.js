const path = require('path');
const merge = require('webpack-merge');
const webpackCommonConfig = require('./webpack.common.js');

const config = merge(webpackCommonConfig, {
    mode: 'development',
    module: {
        rules: [{
            test: /.(ttf|otf|eot|svg|woff(2)?)(\?[a-z0-9]+)?$/,
            use: {
                loader: "file-loader",
                options: {
                    name: "/polls/fonts/[name].[ext]",
                    emitFile: false
                }
            }
        }]
    },
    entry: {
        pollsManagement: './src/main/webapp/vue-app/pollsManagement.js',
        polls: './src/main/webapp/vue-app/polls.js'

    },
    output: {
        path: path.join(__dirname, 'target/polls/'),
        filename: 'js/[name].bundle.js'
    },
    externals: {
        jquery: '$',
        vuetify: 'Vuetify'
    }
});

module.exports = config;