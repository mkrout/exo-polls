const path = require('path');
const ExtractTextWebpackPlugin = require('extract-text-webpack-plugin');
// change the server path to your server locati/on path
const exoServerPath = "E:/eXo/Servers/platform-6.0.0-M34/";

const config = {
    context: path.resolve(__dirname, '.'),
    module: {
        rules: [{
                test: /\.css$/,
                use: ['vue-style-loader', 'css-loader']
            },
            {
                test: /\.less$/,
                use: ExtractTextWebpackPlugin.extract({
                    fallback: 'vue-style-loader',
                    use: [{
                            loader: 'css-loader',
                            options: {
                                sourceMap: true,
                                minimize: true
                            }
                        },
                        {
                            loader: 'less-loader',
                            options: {
                                sourceMap: true,
                                minimize: true
                            }
                        }
                    ]
                })
            },
            {
                test: /\.vue$/,
                use: [
                    'vue-loader',
                    'eslint-loader',
                ]
            }
        ]
    },
    externals: {
        vuetify: 'Vuetify',
        vue: 'Vue',
        jquery: '$'
    },
    plugins: [
        new ExtractTextWebpackPlugin('css/pollsManagement.css')
    ]

};

module.exports = config;