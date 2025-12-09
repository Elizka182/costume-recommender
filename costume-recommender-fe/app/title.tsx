"use client";

import React, { useState } from "react";
import SearchForm from "./searchForm";
import AddForm from "./addForm";
import Result from "./result";

export default function Title() {
    const [currentView, setCurrentView] = useState("home");
    const [costumeResult, setCostumeResult] = useState(null);


    const renderView = () => {
        switch (currentView) {
            case "search":
                return <SearchForm onResultFound={(data) => {
                    setCostumeResult(data);
                    setCurrentView("result");
                }} />;
            case "result":
                return costumeResult ? <Result costumeData={costumeResult} onBack={() => setCurrentView("search")} /> : null;
            case "add":
                return <AddForm />;
            default:
                return (
                    <div className="p-8 flex items-center justify-center min-h-[500px]">
                        <div className="backdrop-blur-2xl bg-gradient-to-br from-white/10 to-white/5 border border-white/20 rounded-3xl p-16 shadow-2xl text-center relative overflow-hidden group">
                            <div className="absolute inset-0 bg-gradient-to-br from-orange-500/5 via-purple-500/5 to-transparent"></div>
                            <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-purple-400/50 to-transparent"></div>
                            <div className="absolute bottom-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-orange-400/50 to-transparent"></div>
                            <div className="relative z-10">
                                <div className="text-7xl mb-6 animate-bounce">🎭</div>
                                <p className="text-purple-100 text-xl font-medium mb-2">
                                    Welcome to Halloween Costume Recommender
                                </p>
                                <p className="text-purple-300/70">
                                    Select an option from the header to begin your journey
                                </p>
                            </div>
                        </div>
                    </div>
                );
        }
    };

    return (
        <div className="w-full min-h-screen bg-gradient-to-br from-black via-violet-950 to-black relative overflow-hidden">
            {/* Animated background elements with neon glow */}
            <div className="absolute inset-0 overflow-hidden pointer-events-none">
                <div className="absolute top-20 left-10 w-96 h-96 bg-purple-600/30 rounded-full blur-3xl animate-pulse-slow"></div>
                <div
                    className="absolute bottom-20 right-10 w-[500px] h-[500px] bg-orange-500/20 rounded-full blur-3xl animate-pulse-slow"
                    style={{ animationDelay: "2s" }}
                ></div>
                <div
                    className="absolute top-1/2 left-1/3 w-80 h-80 bg-violet-500/20 rounded-full blur-3xl animate-pulse-slow"
                    style={{ animationDelay: "4s" }}
                ></div>

                {/* Subtle sparkle effects */}
                <div className="absolute top-1/4 right-1/4 w-2 h-2 bg-orange-400 rounded-full animate-ping"></div>
                <div
                    className="absolute bottom-1/3 left-1/4 w-2 h-2 bg-purple-400 rounded-full animate-ping"
                    style={{ animationDelay: "1s" }}
                ></div>
                <div
                    className="absolute top-2/3 right-1/3 w-2 h-2 bg-white rounded-full animate-ping"
                    style={{ animationDelay: "3s" }}
                ></div>
            </div>

            {/* Content */}
            <div className="relative z-10">
                <header className="backdrop-blur-2xl bg-gradient-to-r from-violet-950/60 via-violet-900/50 to-violet-950/60 border-b border-white/10 shadow-2xl relative">
                    {/* Neon top border */}
                    <div className="absolute top-0 left-0 w-full h-px bg-gradient-to-r from-transparent via-purple-400/80 to-transparent"></div>

                    <div className="container mx-auto px-8 py-8 flex flex-col items-center">
                        <h1 className="text-5xl font-bold mb-8 relative text-center">
                            <span className="text-transparent bg-clip-text bg-gradient-to-r from-orange-400 via-purple-300 to-orange-400 relative z-10">
                                Halloween Costume Recommender
                            </span>
                            <div className="absolute inset-0 blur-xl bg-gradient-to-r from-orange-500/50 via-purple-500/50 to-orange-500/50 -z-10"></div>
                        </h1>

                        <div className="flex gap-5">
                            {/* Search Button */}
                            <button
                                onClick={() => setCurrentView("search")}
                                className="group relative backdrop-blur-xl bg-gradient-to-br from-white/15 to-white/5 hover:from-white/25 hover:to-white/10 border border-purple-400/40 hover:border-purple-400/80 text-white px-10 py-4 rounded-2xl font-semibold transition-all duration-300 shadow-lg hover:shadow-purple-500/50 overflow-hidden"
                            >
                                <div className="absolute inset-0 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                                    <div className="absolute inset-0 bg-gradient-to-r from-purple-500/20 to-purple-600/20 blur-xl"></div>
                                </div>
                                <div className="absolute top-0 left-0 w-full h-1/2 bg-gradient-to-b from-white/20 to-transparent opacity-50 rounded-t-2xl"></div>
                                <span className="relative z-10 flex items-center gap-3 text-lg">
                                    <span className="text-2xl group-hover:scale-110 transition-transform duration-300">🔍</span>
                                    Search
                                </span>
                            </button>

                            {/* Add Costume Button */}
                            <button
                                onClick={() => setCurrentView("add")}
                                className="group relative backdrop-blur-xl bg-gradient-to-br from-orange-600/90 to-orange-500/80 hover:from-orange-500 hover:to-orange-400 border border-orange-400/60 hover:border-orange-300 text-white px-10 py-4 rounded-2xl font-semibold transition-all duration-300 shadow-lg hover:shadow-orange-500/60 overflow-hidden"
                            >
                                <div className="absolute inset-0 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                                    <div className="absolute inset-0 bg-orange-400/30 blur-xl"></div>
                                </div>
                                <div className="absolute top-0 left-0 w-full h-1/2 bg-gradient-to-b from-white/30 to-transparent opacity-60 rounded-t-2xl"></div>
                                <span className="relative z-10 flex items-center gap-3 text-lg">
                                    <span className="text-2xl group-hover:scale-110 transition-transform duration-300">✨</span>
                                    Add Costume
                                </span>
                            </button>
                        </div>
                    </div>

                    {/* Bottom neon border */}
                    <div className="absolute bottom-0 left-0 w-full h-px bg-gradient-to-r from-transparent via-orange-400/50 to-transparent"></div>
                </header>

                <main className="container mx-auto">{renderView()}</main>
            </div>

            <style jsx>{`
                @keyframes pulse-slow {
                    0%, 100% { opacity: 0.3; transform: scale(1); }
                    50% { opacity: 0.6; transform: scale(1.1); }
                }
                .animate-pulse-slow {
                    animation: pulse-slow 6s ease-in-out infinite;
                }
            `}</style>
        </div>
    );
}
