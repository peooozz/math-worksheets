import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import confetti from 'canvas-confetti';
import { ArrowRight, Star, RotateCcw } from 'lucide-react';
import { playSound } from '../utils/sound';

const OBJECTS = ['🍎', '⭐', '🧸', '🎈', '🐶', '🚗', '🍪'];

const SubtractionModule = () => {
  const [total, setTotal] = useState(1);
  const [removed, setRemoved] = useState(0);
  const [objectType, setObjectType] = useState(OBJECTS[0]);
  const [options, setOptions] = useState([]);
  const [isCorrect, setIsCorrect] = useState(false);
  const [score, setScore] = useState(0);

  const generateProblem = () => {
    const t = Math.floor(Math.random() * 10) + 1; // 1-10
    const r = Math.floor(Math.random() * (t + 1)); // 0-t
    setTotal(t);
    setRemoved(r);
    setObjectType(OBJECTS[Math.floor(Math.random() * OBJECTS.length)]);
    setIsCorrect(false);
    
    const ans = t - r;
    const opts = new Set([ans]);
    while(opts.size < 3) {
      let rand = Math.floor(Math.random() * 11);
      if (rand !== ans) opts.add(rand);
    }
    setOptions(Array.from(opts).sort((a, b) => a - b));
  };

  useEffect(() => {
    generateProblem();
  }, []);

  const handleAnswer = (ans) => {
    if (ans === total - removed) {
      setIsCorrect(true);
      setScore(s => s + 1);
      playSound('success');
      confetti({
        particleCount: 100,
        spread: 70,
        origin: { y: 0.6 },
        colors: ['#AEC6CF', '#FFB7B2', '#77DD77', '#FDFD96']
      });
    } else {
      playSound('error');
    }
  };

  return (
    <div className="h-full flex flex-col p-2 md:p-4 bg-white items-center overflow-y-auto">
      <div className="w-full flex justify-between items-center mb-6 text-slate-500 shrink-0">
        <button onClick={generateProblem} className="p-2 bg-slate-100 rounded-full hover:bg-slate-200">
            <RotateCcw size={20} />
        </button>
        <div className="flex items-center gap-1 text-pastel-orange bg-orange-50 px-3 py-1 rounded-full border border-orange-100">
          <Star fill="currentColor" size={20} />
          <span className="font-bold">{score}</span>
        </div>
      </div>

      <div className="flex-1 flex flex-col justify-center items-center w-full gap-4 md:gap-8">
        
        {/* Visual Equation */}
        <div className="relative p-6 bg-pastel-pink/10 rounded-3xl min-h-[200px] flex items-center justify-center w-full max-w-md">
           <div className="flex flex-wrap gap-4 justify-center">
             {Array.from({ length: total }).map((_, i) => (
               <motion.div
                 key={`obj-${i}`}
                 initial={{ opacity: 0, scale: 0 }}
                 animate={{ 
                   opacity: i < (total - removed) ? 1 : 0.3, 
                   scale: 1,
                 }}
                 className="text-5xl md:text-6xl relative select-none"
               >
                 {objectType}
                 {i >= (total - removed) && (
                   <motion.div 
                     initial={{ scale: 0 }}
                     animate={{ scale: 1 }}
                     transition={{ delay: 0.5 + (i - (total-removed)) * 0.2 }}
                     onAnimationStart={() => playSound('pop')}
                     className="absolute inset-0 flex items-center justify-center text-red-500/60 font-bold"
                   >
                     ✕
                   </motion.div>
                 )}
               </motion.div>
             ))}
           </div>
           
           <div className="absolute top-4 right-4 text-4xl font-bold text-slate-300 opacity-20">{total}</div>
        </div>

        {/* Text Equation */}
        <div className="text-4xl font-bold text-slate-700 mt-2">
          {total} - {removed} = <span className="inline-block w-16 border-b-4 border-slate-300 text-center text-pastel-pink">{isCorrect ? total - removed : '?'}</span>
        </div>

        {/* Options */}
        <div className="flex gap-4 md:gap-8 mt-4 flex-wrap justify-center w-full">
            {options.map((opt) => (
                <motion.button
                    key={opt}
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                    onClick={() => handleAnswer(opt)}
                    className={`
                        w-20 h-20 md:w-24 md:h-24 rounded-2xl text-3xl md:text-4xl font-bold shadow-lg transition-colors
                        ${isCorrect && opt === total - removed 
                            ? 'bg-green-400 text-white ring-4 ring-green-200' 
                            : 'bg-white text-slate-600 hover:bg-slate-50 ring-2 ring-slate-100'}
                    `}
                    disabled={isCorrect}
                >
                    {opt}
                </motion.button>
            ))}
        </div>

        {isCorrect && (
            <motion.button
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                onClick={generateProblem}
                className="mt-8 bg-pastel-blue text-white font-bold py-3 px-8 rounded-full shadow-lg flex items-center gap-2 hover:brightness-110"
            >
                Next Problem <ArrowRight />
            </motion.button>
        )}

      </div>
    </div>
  );
};

export default SubtractionModule;
