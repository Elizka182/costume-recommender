"use client";

import React, { useState, useEffect } from 'react';

interface ResultFormProps {
    costumeData: {
        name: string;
        description: string;
        imageurl: string;
    };
    onBack: () => void;
}

export default function Result({ costumeData, onBack }: ResultFormProps) {
    const [imageBase64, setImageBase64] = useState<string>("");
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        const fetchImage = async () => {
            try {
                setLoading(true);
                const response = await fetch("http://localhost:8080/costume/findImage", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ imageurl: costumeData.imageurl })
                });

                if (!response.ok) {
                    throw new Error(`Failed to fetch image: ${response.status}`);
                }

                const blob = await response.blob();

                const reader = new FileReader();
                reader.onloadend = () => {
                    const base64data = reader.result?.toString().split(",")[1] || "";
                    setImageBase64(base64data);
                    setLoading(false);
                };
                reader.readAsDataURL(blob);

            } catch (err) {
                console.error("❌ Error fetching image:", err);
                setError("Failed to load costume image");
                setLoading(false);
            }
        };

        if (costumeData.imageurl) {
            fetchImage();
        }
    }, [costumeData.imageurl]);

    return (
        <div className="p-8">
            <div className="max-w-4xl mx-auto">
                {/* Back Button */}
                <button
                    onClick={onBack}
                    className="mb-6 backdrop-blur-xl bg-black/40 hover:bg-black/60 border-2 border-[#9B5CFF]/40 hover:border-[#9B5CFF]/80 text-white px-6 py-3 rounded-xl font-semibold transition-all duration-300 shadow-lg hover:shadow-[#9B5CFF]/30"
                >
                    ← Back to Search
                </button>

                {/* Costume Card */}
                <div className="backdrop-blur-2xl bg-black/60 border-2 border-[#FF7A00]/40 rounded-3xl p-8 md:p-12 shadow-2xl relative overflow-hidden group hover:border-[#FF7A00]/70 transition-all duration-500">
                    {/* Background effects */}
                    <div className="absolute inset-0 bg-gradient-to-br from-[#FF7A00]/5 via-[#9B5CFF]/5 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
                    <div className="absolute -top-32 -right-32 w-64 h-64 bg-[#FF7A00]/20 rounded-full blur-3xl"></div>
                    <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-[#FF7A00]/80 to-transparent"></div>
                    <div className="absolute bottom-0 left-0 w-full h-1 bg-gradient-to-r from-transparent via-[#9B5CFF]/80 to-transparent"></div>

                    <div className="relative z-10">
                        {/* Header */}
                        <div className="text-center mb-8">
                            <div className="text-6xl mb-4">🎃</div>
                            <h2 className="text-4xl md:text-5xl font-bold text-white mb-2">
                                Your Perfect Costume!
                            </h2>
                            <p className="text-gray-400 text-lg">We found the perfect match for you</p>
                        </div>

                        {/* Costume Content */}
                        <div className="grid md:grid-cols-2 gap-8 items-start">
                            {/* Image Section */}
                            <div className="relative">
                                <div className="backdrop-blur-xl bg-black/40 border-2 border-[#9B5CFF]/40 rounded-2xl p-4 shadow-xl overflow-hidden">
                                    {loading ? (
                                        <div className="aspect-square flex items-center justify-center">
                                            <div className="animate-spin rounded-full h-16 w-16 border-4 border-[#FF7A00] border-t-transparent"></div>
                                        </div>
                                    ) : error ? (
                                        <div className="aspect-square flex items-center justify-center bg-black/40 rounded-xl">
                                            <p className="text-red-400 text-center px-4">{error}</p>
                                        </div>
                                    ) : (
                                        <img
                                            src={`data:image/jpeg;base64,${imageBase64}`}
                                            alt={costumeData.name}
                                            className="w-full h-auto rounded-xl object-cover"
                                            onError={() => setError("Failed to display image")}
                                        />
                                    )}
                                </div>
                            </div>

                            {/* Info Section */}
                            <div className="space-y-6">
                                {/* Costume Name */}
                                <div className="backdrop-blur-xl bg-black/40 border-2 border-[#FF7A00]/40 rounded-2xl p-6 shadow-xl">
                                    <h3 className="text-[#FF7A00] text-sm font-semibold mb-2 uppercase tracking-wider">
                                        Costume Name
                                    </h3>
                                    <p className="text-white text-2xl md:text-3xl font-bold">
                                        {costumeData.name}
                                    </p>
                                </div>

                                {/* Description */}
                                <div className="backdrop-blur-xl bg-black/40 border-2 border-[#9B5CFF]/40 rounded-2xl p-6 shadow-xl">
                                    <h3 className="text-[#9B5CFF] text-sm font-semibold mb-3 uppercase tracking-wider">
                                        Description
                                    </h3>
                                    <p className="text-gray-300 text-lg leading-relaxed">
                                        {costumeData.description}
                                    </p>
                                </div>

                                {/* Action Buttons */}
                                <div className="flex flex-col sm:flex-row gap-4 mt-8">
                                    <button
                                        onClick={onBack}
                                        className="flex-1 backdrop-blur-xl bg-black/60 hover:bg-black/80 border-2 border-[#FF7A00]/40 hover:border-[#FF7A00]/80 text-white px-6 py-4 rounded-2xl font-bold transition-all duration-300 shadow-xl hover:shadow-[#FF7A00]/30 relative overflow-hidden group"
                                    >
                                        <div className="absolute inset-0 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                                            <div className="absolute inset-0 bg-[#FF7A00]/10"></div>
                                        </div>
                                        <div className="absolute top-0 left-0 w-full h-1/2 bg-gradient-to-b from-white/20 to-transparent opacity-50 rounded-t-2xl"></div>
                                        <span className="relative z-10 flex items-center justify-center gap-2">
                      🔍 Search Again
                    </span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}