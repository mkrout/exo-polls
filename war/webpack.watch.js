const path = require('path');
const merge = require('webpack-merge');


const webpackProductionConfig = require('./webpack.prod.js');

module.exports = merge(webpackProductionConfig, {
    output: {
        path: 'D:\\\eXo\\Servers\\platform-6.0.0-M34\\webapps\\polls',
        filename: 'js/[name].bundle.js'
    }
});