import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import confetti from 'canvas-confetti';
import { ArrowRight, Star, RotateCcw } from 'lucide-react';
import { playSound } from '../utils/sound';

const OBJECTS = ['🍎', '⭐', '🧸', '🎈', '🐶', '🚗', '🍪'];

const AdditionModule = () => {
  const [num1, setNum1] = useState(1);
  const [num2, setNum2] = useState(1);
  const [objectType, setObjectType] = useState(OBJECTS[0]);
  const [options, setOptions] = useState([]);
  const [isCorrect, setIsCorrect] = useState(false);
  const [score, setScore] = useState(0);
  const [countedIndices, setCountedIndices] = useState(new Set()); // Track tapped objects

  const generateProblem = () => {
    const n1 = Math.floor(Math.random() * 6); // 0-5
    const n2 = Math.floor(Math.random() * (11 - n1)); // Ensures sum <= 10
    setNum1(n1);
    setNum2(n2);
    setObjectType(OBJECTS[Math.floor(Math.random() * OBJECTS.length)]);
    setIsCorrect(false);
    setCountedIndices(new Set());
    
    // Generate options
    const sum = n1 + n2;
    const opts = new Set([sum]);
    while(opts.size < 3) {
      let r = Math.floor(Math.random() * 11);
      if (r !== sum) opts.add(r);
    }
    setOptions(Array.from(opts).sort((a, b) => a - b));
  };

  useEffect(() => {
    generateProblem();
  }, []);

  const handleAnswer = (ans) => {
    if (ans === num1 + num2) {
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

  const handleObjectTap = (index) => {
    if (countedIndices.has(index)) return;
    
    const newSet = new Set(countedIndices);
    newSet.add(index);
    setCountedIndices(newSet);
    
    // Play count sound (simulated with click for now, ideally voice "One", "Two")
    playSound('click');
  };

  const totalObjects = num1 + num2;

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
        <div className="flex items-center justify-center gap-2 md:gap-4 flex-wrap text-5xl md:text-7xl select-none">
          {/* Group 1 */}
          <div className="flex gap-2 p-4 bg-pastel-blue/20 rounded-3xl min-w-[100px] justify-center flex-wrap relative">
            {Array.from({ length: num1 }).map((_, i) => {
                const globalIndex = i;
                const isCounted = countedIndices.has(globalIndex);
                return (
                  <motion.div
                    key={`n1-${i}`}
                    initial={{ scale: 0 }}
                    animate={{ scale: isCounted ? 1.2 : 1 }}
                    whileTap={{ scale: 0.8 }}
                    onClick={() => handleObjectTap(globalIndex)}
                    className={`cursor-pointer transition-opacity ${isCounted ? 'opacity-100' : 'opacity-90'}`}
                  >
                    {objectType}
                    {isCounted && <span className="absolute -top-2 right-0 text-sm bg-black/50 text-white px-1 rounded-full">{globalIndex + 1}</span>}
                  </motion.div>
                );
            })}
            {num1 === 0 && <span className="opacity-30 text-4xl">0</span>}
            <div className="absolute -bottom-8 text-2xl font-bold text-slate-400">{num1}</div>
          </div>

          <div className="text-slate-400 font-bold text-4xl">+</div>

          {/* Group 2 */}
          <div className="flex gap-2 p-4 bg-pastel-green/20 rounded-3xl min-w-[100px] justify-center flex-wrap relative">
            {Array.from({ length: num2 }).map((_, i) => {
                const globalIndex = num1 + i;
                const isCounted = countedIndices.has(globalIndex);
                return (
                  <motion.div
                    key={`n2-${i}`}
                    initial={{ scale: 0 }}
                    animate={{ scale: isCounted ? 1.2 : 1 }}
                    transition={{ delay: 0.2 + i * 0.1 }}
                    whileTap={{ scale: 0.8 }}
                    onClick={() => handleObjectTap(globalIndex)}
                    className={`cursor-pointer transition-opacity ${isCounted ? 'opacity-100' : 'opacity-90'}`}
                  >
                    {objectType}
                    {isCounted && <span className="absolute -top-2 right-0 text-sm bg-black/50 text-white px-1 rounded-full">{globalIndex + 1}</span>}
                  </motion.div>
                );
            })}
             {num2 === 0 && <span className="opacity-30 text-4xl">0</span>}
             <div className="absolute -bottom-8 text-2xl font-bold text-slate-400">{num2}</div>
          </div>
        </div>
        
        <div className="text-4xl font-bold text-slate-300 mt-4">=</div>

        {/* Answer Options */}
        <div className="flex gap-4 md:gap-8 mt-4 flex-wrap justify-center w-full">
            {options.map((opt) => (
                <motion.button
                    key={opt}
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                    onClick={() => handleAnswer(opt)}
                    className={`
                        w-20 h-20 md:w-24 md:h-24 rounded-2xl text-3xl md:text-4xl font-bold shadow-lg transition-colors
                        ${isCorrect && opt === num1 + num2 
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

export default AdditionModule;
