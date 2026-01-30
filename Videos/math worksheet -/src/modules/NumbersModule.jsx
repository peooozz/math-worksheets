import React, { useState, useRef, useEffect, useMemo } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import confetti from 'canvas-confetti';
import { RefreshCw, ArrowRight, Check, Eraser } from 'lucide-react';
import { playSound } from '../utils/sound';

// Simple SVG paths for numbers 0-9
// Optimized for single continuous stroke where possible, or handled as multi-stroke
const NUMBER_PATHS = {
  0: ["M 100,20 A 40,40 0 1,1 100,180 A 40,40 0 1,1 100,20"],
  1: ["M 80,40 L 100,20 L 100,180"],
  2: ["M 60,60 C 60,20 140,20 140,60 C 140,100 60,180 60,180 L 150,180"],
  3: ["M 60,40 L 140,40 L 100,80 M 100,80 C 150,80 150,160 100,160 C 60,160 60,120 60,120"],
  4: ["M 120,180 L 120,20 L 40,120 L 140,120"], // Simplified to 1 stroke for ease, or could be 2
  5: ["M 130,20 L 70,20 L 70,80 C 70,80 140,80 140,140 C 140,180 60,180 60,140"],
  6: ["M 120,20 C 60,60 60,180 100,180 C 140,180 140,120 100,120 C 60,120 65,110 120,20"],
  7: ["M 40,20 L 160,20 L 80,180"],
  8: ["M 100,100 C 140,100 140,20 100,20 C 60,20 60,100 100,100 C 140,100 140,180 100,180 C 60,180 60,100 100,100"],
  9: ["M 80,180 C 140,140 140,20 100,20 C 60,20 60,100 100,100 C 140,100 135,110 80,180"],
  10: ["M 60,50 L 80,30 L 80,170", "M 140,30 A 30,30 0 1,1 140,170 A 30,30 0 1,1 140,30"]
};

const NUMBER_NAMES = ["Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"];

const NumbersModule = () => {
  const [currentNumber, setCurrentNumber] = useState(0);
  const [isCompleted, setIsCompleted] = useState(false);
  const [userPaths, setUserPaths] = useState([]); // Array of { points: [], color: string }
  const [currentPath, setCurrentPath] = useState([]);
  
  // Tracing validation state
  const [targetPoints, setTargetPoints] = useState([]); // Array of { x, y, covered }
  const pathRefs = useRef([]);
  const svgRef = useRef(null);

  // Initialize target points from SVG paths
  useEffect(() => {
    // Small delay to ensure DOM is ready
    const timer = setTimeout(() => {
      const newTargetPoints = [];
      pathRefs.current.forEach((path) => {
        if (!path) return;
        const length = path.getTotalLength();
        const points = [];
        // Sample every 10 units
        for (let i = 0; i <= length; i += 10) {
          const p = path.getPointAtLength(i);
          points.push({ x: p.x, y: p.y, covered: false });
        }
        newTargetPoints.push(points);
      });
      setTargetPoints(newTargetPoints);
    }, 100);
    
    return () => clearTimeout(timer);
  }, [currentNumber]);

  // Reset when number changes
  useEffect(() => {
    setUserPaths([]);
    setCurrentPath([]);
    setIsCompleted(false);
    pathRefs.current = []; // Reset refs
    playSound('pop');
  }, [currentNumber]);

  const handleStart = (e) => {
    if (isCompleted) return;
    document.body.classList.add('no-scroll');
    const point = getPoint(e);
    setCurrentPath([point]);
    playSound('click');
  };

  const handleMove = (e) => {
    if (isCompleted || currentPath.length === 0) return;
    const point = getPoint(e);
    setCurrentPath((prev) => [...prev, point]);
    
    // Check coverage
    checkCoverage(point);
  };

  const handleEnd = () => {
    document.body.classList.remove('no-scroll');
    if (currentPath.length > 0) {
      setUserPaths((prev) => [...prev, { points: currentPath }]);
      setCurrentPath([]);
    }
  };

  const getPoint = (e) => {
    const svg = svgRef.current;
    if (!svg) return { x: 0, y: 0 };
    
    let clientX, clientY;
    if (e.changedTouches) {
      clientX = e.changedTouches[0].clientX;
      clientY = e.changedTouches[0].clientY;
    } else {
      clientX = e.clientX;
      clientY = e.clientY;
    }

    const rect = svg.getBoundingClientRect();
    return {
      x: (clientX - rect.left) * (200 / rect.width),
      y: (clientY - rect.top) * (200 / rect.height)
    };
  };

  const checkCoverage = (userPoint) => {
    let changed = false;
    let allCompleted = true;

    const newTargetPoints = targetPoints.map(subPath => {
      const newSubPath = subPath.map(pt => {
        if (pt.covered) return pt;
        // Distance check (tolerance 15 units)
        const dist = Math.sqrt(Math.pow(pt.x - userPoint.x, 2) + Math.pow(pt.y - userPoint.y, 2));
        if (dist < 20) {
          changed = true;
          return { ...pt, covered: true };
        }
        return pt;
      });

      // Check if this subpath is mostly completed (> 80%)
      const coveredCount = newSubPath.filter(p => p.covered).length;
      if (coveredCount / newSubPath.length < 0.8) {
        allCompleted = false;
      }
      return newSubPath;
    });

    if (changed) {
      setTargetPoints(newTargetPoints);
      if (allCompleted && !isCompleted) {
        completeLevel();
      }
    }
  };

  const completeLevel = () => {
    setIsCompleted(true);
    playSound('success');
    confetti({
      particleCount: 150,
      spread: 70,
      origin: { y: 0.6 },
      colors: ['#AEC6CF', '#FFB7B2', '#77DD77', '#FDFD96']
    });
  };

  const nextNumber = () => {
    if (currentNumber < 10) setCurrentNumber(c => c + 1);
  };

  const prevNumber = () => {
    if (currentNumber > 0) setCurrentNumber(c => c - 1);
  };

  const reset = () => {
    setUserPaths([]);
    setIsCompleted(false);
    // Reset coverage
    setTargetPoints(prev => prev.map(sub => sub.map(p => ({ ...p, covered: false }))));
  };

  // Convert array of points to SVG path d string
  const pointsToPath = (points) => {
    if (points.length === 0) return "";
    const d = points.map((p, i) => `${i === 0 ? 'M' : 'L'} ${p.x},${p.y}`).join(' ');
    return d;
  };

  const currentPaths = NUMBER_PATHS[currentNumber];

  return (
    <div className="h-full flex flex-col items-center p-2 md:p-4 bg-white overflow-y-auto">
      {/* Header */}
      <div className="text-center mb-2 md:mb-4 shrink-0">
        <h2 className="text-3xl md:text-4xl font-bold text-pastel-blue mb-1">{NUMBER_NAMES[currentNumber]}</h2>
        <div className="flex gap-4 justify-center items-center">
            <button onClick={prevNumber} disabled={currentNumber === 0} className="p-2 text-slate-300 hover:text-slate-500 disabled:opacity-0">
                <ArrowRight className="rotate-180" />
            </button>
            <div className="text-5xl md:text-6xl font-bold text-slate-800 w-20">{currentNumber}</div>
            <button onClick={nextNumber} disabled={currentNumber === 10} className="p-2 text-slate-300 hover:text-slate-500 disabled:opacity-0">
                <ArrowRight />
            </button>
        </div>
      </div>

      {/* Main Tracing Area */}
      <div className="relative w-full max-w-[300px] aspect-square bg-slate-50 rounded-3xl border-4 border-dashed border-pastel-blue/30 flex items-center justify-center touch-none shadow-inner shrink-0">
        <svg
          ref={svgRef}
          viewBox="0 0 200 200"
          className="w-full h-full"
          onTouchStart={handleStart}
          onTouchMove={handleMove}
          onTouchEnd={handleEnd}
          onMouseDown={handleStart}
          onMouseMove={handleMove}
          onMouseUp={handleEnd}
          onMouseLeave={handleEnd}
          style={{ cursor: isCompleted ? 'default' : 'crosshair' }}
        >
          {/* Target Paths (Background) */}
          {currentPaths.map((d, i) => (
            <g key={i}>
                <path 
                    d={d} 
                    fill="none" 
                    stroke="#e2e8f0" 
                    strokeWidth="25" 
                    strokeLinecap="round" 
                    strokeLinejoin="round" 
                />
                <path 
                    ref={el => pathRefs.current[i] = el}
                    d={d} 
                    fill="none" 
                    stroke="#94a3b8" 
                    strokeWidth="2" 
                    strokeDasharray="4,4" 
                />
            </g>
          ))}
          
          {/* Debug: Show Coverage Points (optional, hidden in prod) */}
          {/* {targetPoints.flat().map((p, i) => (
            <circle key={i} cx={p.x} cy={p.y} r="2" fill={p.covered ? "green" : "red"} opacity="0.5" />
          ))} */}

          {/* User Paths */}
          {userPaths.map((p, i) => (
            <path key={i} d={pointsToPath(p.points)} fill="none" stroke="#77DD77" strokeWidth="20" strokeLinecap="round" strokeLinejoin="round" opacity="0.8" />
          ))}
          {/* Current Drawing Path */}
          <path d={pointsToPath(currentPath)} fill="none" stroke="#77DD77" strokeWidth="20" strokeLinecap="round" strokeLinejoin="round" opacity="0.8" />
        </svg>

        {isCompleted && (
            <motion.div 
                initial={{ scale: 0, rotate: -10 }}
                animate={{ scale: 1, rotate: 0 }}
                className="absolute inset-0 flex items-center justify-center pointer-events-none"
            >
                <div className="bg-white/90 p-4 rounded-full shadow-xl">
                    <Check size={60} className="text-green-500" />
                </div>
            </motion.div>
        )}
      </div>

      {/* Controls */}
      <div className="w-full max-w-[300px] mt-4 flex gap-4 justify-between shrink-0">
        <button onClick={reset} className="p-3 rounded-full bg-slate-100 hover:bg-slate-200 text-slate-600 transition-colors" aria-label="Clear">
          <RefreshCw size={24} />
        </button>

        {isCompleted && (
          <motion.button
            initial={{ scale: 0 }}
            animate={{ scale: 1 }}
            onClick={nextNumber}
            className="flex-1 bg-pastel-green text-white font-bold py-3 px-6 rounded-full shadow-lg flex items-center justify-center gap-2 hover:brightness-110"
          >
            Next Number <ArrowRight size={24} />
          </motion.button>
        )}
      </div>

      {/* Practice Grid */}
      <div className="w-full max-w-lg mt-8 p-4 bg-slate-50 rounded-2xl border border-slate-100">
        <h3 className="text-slate-400 font-bold mb-2 text-sm uppercase tracking-wider">Practice Grid</h3>
        <div className="grid grid-cols-5 gap-2">
            {Array.from({ length: 5 }).map((_, i) => (
                <div key={i} className="aspect-square bg-white rounded-xl border border-slate-200 flex items-center justify-center relative group">
                    <span className="text-4xl text-slate-200 font-bold select-none absolute">{currentNumber}</span>
                    {/* Small version could be interactive too, but for now just visual reference */}
                </div>
            ))}
        </div>
      </div>
    </div>
  );
};

export default NumbersModule;
