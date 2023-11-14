module.exports = {
  devServer: {
    proxy: {
      "": {
        target: "http://localhost:8080", // Your backend server address
        changeOrigin: true,
        pathRewrite: {
          "^": "", // Remove the /api prefix when making requests
        },
      },
    },
  },
};
