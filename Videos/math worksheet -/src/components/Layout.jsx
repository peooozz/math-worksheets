import React from 'react';
import { BookOpen, Plus, Minus, Calculator } from 'lucide-react';

const modules = [
  { id: 'numbers', label: 'Numbers', icon: Calculator, color: 'bg-pastel-blue' },
  { id: 'addition', label: 'Addition', icon: Plus, color: 'bg-pastel-green' },
  { id: 'subtraction', label: 'Subtraction', icon: Minus, color: 'bg-pastel-pink' },
];

const Layout = ({ currentModule, onModuleChange, children }) => {
  return (
    <div className="min-h-screen bg-slate-50 flex flex-col font-comic select-none">
      {/* Header / Navigation */}
      <header className="p-4 bg-white shadow-md z-10 sticky top-0">
        <div className="max-w-4xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl md:text-3xl font-bold text-slate-800 flex items-center gap-2">
            <span className="text-3xl">✏️</span> 
            <span className="hidden sm:inline">Math Fun</span>
          </h1>
          
          <nav className="flex gap-2">
            {modules.map((mod) => (
              <button
                key={mod.id}
                onClick={() => onModuleChange(mod.id)}
                className={`
                  flex items-center gap-2 px-3 py-2 rounded-full transition-all transform active:scale-95
                  ${currentModule === mod.id 
                    ? `${mod.color} text-white shadow-lg scale-105 font-bold` 
                    : 'bg-slate-100 text-slate-600 hover:bg-slate-200'}
                `}
              >
                <mod.icon size={20} />
                <span className="hidden sm:inline">{mod.label}</span>
              </button>
            ))}
          </nav>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 p-4 max-w-4xl mx-auto w-full flex flex-col">
        <div className="flex-1 bg-white rounded-3xl shadow-xl overflow-hidden relative min-h-[60vh]">
          {children}
        </div>
      </main>

      {/* Footer */}
      <footer className="p-4 text-center text-slate-400 text-sm">
        Math Worksheet for Kids (Ages 4-7)
      </footer>
    </div>
  );
};

export default Layout;
