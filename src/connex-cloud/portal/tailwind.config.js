module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [],

  //https://stackoverflow.com/questions/72356953/how-to-use-dynamic-class-of-tailwindcss-like-this-in-project-of-vue3-and-vite
  safelist: [

    {
      pattern: /bg-(red|green|blue)-(100|200|300|400|500)/
    }
  ]
}
