var path = require('path');
var webpack = require('webpack');
module.exports = {
    entry: { path: __dirname + '/javascript/main.js' },
    output: { path: __dirname + "/assets", filename: 'application.js' },
    module: {
        loaders: [
            {
                test: /.jsx?$/,
                loader: 'babel-loader',
                exclude: /node_modules/,
                query: {
                    presets: ['es2015', 'react']
                }
            }
        ]
    },
};
