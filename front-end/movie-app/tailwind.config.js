/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}",],
  theme: {
    extend: {
      colors: {
        bgNav: '#010f1c',  // Define your custom color
        bgWeb:'#849ca8',
        bgMagnify: '#1a2732',
        textNav:'#6e757b'
      },
    },
  },
  plugins: [],
}

