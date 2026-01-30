/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'pastel-blue': '#AEC6CF',
        'pastel-pink': '#FFB7B2',
        'pastel-green': '#77DD77',
        'pastel-yellow': '#FDFD96',
        'pastel-purple': '#B39EB5',
        'pastel-orange': '#FFB347',
      },
      fontFamily: {
        'comic': ['"Comic Sans MS"', '"Chalkboard SE"', '"Comic Neue"', 'sans-serif'],
      }
    },
  },
  plugins: [],
}
