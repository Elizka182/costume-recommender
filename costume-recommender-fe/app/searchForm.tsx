"use client";

import React, { useState } from "react";

const scaryLevels = ["spooky", "funny", "cute", "classic", "dark", "sexy"];
const budgets = ["low", "medium", "high"];
const themes = [
    "vampires", "witches", "zombies", "ghosts", "skeletons", "demons",
    "monsters", "werewolves", "mummies", "movie, cartoon, game characters",
    "villains", "superheroes", "sci-fi characters", "fantasy characters",
    "animals", "food-themed costumes", "memes", "retro", "clowns",
    "historical", "professions", "DIY"
];
const genders = ["unisex", "feminine", "masculine", "does not matter"];
const timeToPrep = ["last-minute", "quick", "medium effort", "high effort"];
const makeUpOptions = ["no", "light", "full"];
const maskOptions = ["yes", "no"];
const groupTypes = ["solo", "couple", "group", "family-friendly"];
const colors = ["black", "red", "white", "pink", "pastel", "green", "blue", "neon"];

interface SearchFormProps {
    onResultFound: (data: any) => void;
}

export default function SearchForm({ onResultFound }: SearchFormProps) {
    const [form, setForm] = useState<any>({
        scaryLevel: "",
        budget: "",
        themes: [] as string[],
        gender: "",
        timePrep: "",
        makeUp: "",
        mask: "",
        groupType: "",
        colors: [] as string[],
        city: "",
        country: "",
        age: ""
    });

    const handleChange = (key: string, value: any) => {
        setForm((prev: any) => ({ ...prev, [key]: value }));
    };

    const toggleArrayItem = (key: string, value: string) => {
        setForm((prev: any) => {
            const arr = prev[key] as string[];
            if (arr.includes(value)) return { ...prev, [key]: arr.filter((i) => i !== value) };
            else return { ...prev, [key]: [...arr, value] };
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // ✅ Формируем тело запроса строго под твой бэк
        const payload: any = {
            scaryLevel: form.scaryLevel,
            budget: form.budget,
            themes: form.themes,
            gender: form.gender,
            timeToPrep: form.timePrep,
            makeUp: form.makeUp,
            mask: form.mask,
            groupType: form.groupType,
            colors: form.colors,
            city: form.city,
            country: form.country
        };

        // ✅ age — только если введён
        if (form.age !== "" && form.age !== null) {
            payload.age = Number(form.age);
        }

        console.log("✅ Sending payload to backend:", payload);

        try {
            const response = await fetch("http://localhost:8080/costume/search", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                throw new Error(`Server error: ${response.status}`);
            }

            const data = await response.json();
            console.log("✅ Backend response:", data);
            onResultFound(data); //delete


        } catch (error) {
            console.error("❌ Error while fetching costume:", error);
            alert("Something went wrong while searching for a costume 😢");
        }
    };

    return (
        <div className="p-8">
            <div className="backdrop-blur-2xl bg-gradient-to-br from-white/10 to-white/5 border border-purple-400/30 rounded-3xl p-8 shadow-2xl relative overflow-hidden group hover:border-purple-400/60 transition-all duration-500">
                <div className="absolute inset-0 bg-gradient-to-br from-purple-500/0 via-purple-500/5 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
                <div className="absolute -top-24 -right-24 w-48 h-48 bg-purple-500/20 rounded-full blur-3xl"></div>

                <div className="relative z-10">
                    <h2 className="text-3xl font-bold text-white mb-6 flex items-center gap-3">
                        <span className="text-4xl">🔍</span> Find Your Costume
                    </h2>

                    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                        {/* Selects */}
                        <select
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.scaryLevel}
                            onChange={(e) => handleChange("scaryLevel", e.target.value)}
                            required
                        >
                            <option value="">Select Scary Level</option>
                            {scaryLevels.map((lvl) => <option key={lvl}>{lvl}</option>)}
                        </select>

                        <select
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.budget}
                            onChange={(e) => handleChange("budget", e.target.value)}
                            required
                        >
                            <option value="">Select Budget</option>
                            {budgets.map((b) => <option key={b}>{b}</option>)}
                        </select>

                        <select
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.gender}
                            onChange={(e) => handleChange("gender", e.target.value)}
                            required
                        >
                            <option value="">Select Gender Presentation</option>
                            {genders.map((g) => <option key={g}>{g}</option>)}
                        </select>

                        <select
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.timePrep}
                            onChange={(e) => handleChange("timePrep", e.target.value)}
                            required
                        >
                            <option value="">Select Time to Prep</option>
                            {timeToPrep.map((t) => <option key={t}>{t}</option>)}
                        </select>

                        <select
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.makeUp}
                            onChange={(e) => handleChange("makeUp", e.target.value)}
                            required
                        >
                            <option value="">Select Make Up</option>
                            {makeUpOptions.map((m) => <option key={m}>{m}</option>)}
                        </select>

                        <select
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.mask}
                            onChange={(e) => handleChange("mask", e.target.value)}
                            required
                        >
                            <option value="">Do you want a mask?</option>
                            {maskOptions.map((m) => <option key={m}>{m}</option>)}
                        </select>

                        <select
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.groupType}
                            onChange={(e) => handleChange("groupType", e.target.value)}
                            required
                        >
                            <option value="">Select Group Type</option>
                            {groupTypes.map((g) => <option key={g}>{g}</option>)}
                        </select>

                        {/* Themes */}
                        <h3 className="text-white font-semibold mt-4 mb-2">Select Themes</h3>
                        <div className="flex flex-wrap gap-2">
                            {themes.map((t) => (
                                <button
                                    key={t}
                                    type="button"
                                    onClick={() => toggleArrayItem("themes", t)}
                                    className={`px-3 py-1 rounded-full border ${
                                        form.themes.includes(t)
                                            ? "bg-purple-500 text-white"
                                            : "border-purple-400/50 text-purple-200"
                                    } transition-all duration-300`}
                                >
                                    {t}
                                </button>
                            ))}
                        </div>

                        {/* Colors */}
                        <h3 className="text-white font-semibold mt-4 mb-2">Select Colors</h3>
                        <div className="flex flex-wrap gap-2">
                            {colors.map((c) => (
                                <button
                                    key={c}
                                    type="button"
                                    onClick={() => toggleArrayItem("colors", c)}
                                    className={`px-3 py-1 rounded-full border ${
                                        form.colors.includes(c)
                                            ? "bg-purple-500 text-white"
                                            : "border-purple-400/50 text-purple-200"
                                    } transition-all duration-300`}
                                >
                                    {c}
                                </button>
                            ))}
                        </div>

                        {/* Location */}
                        <input
                            type="text"
                            placeholder="City"
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.city}
                            onChange={(e) => handleChange("city", e.target.value)}
                            required
                        />

                        <input
                            type="text"
                            placeholder="Country"
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.country}
                            onChange={(e) => handleChange("country", e.target.value)}
                            required
                        />

                        {/* ✅ Age */}
                        <input
                            type="number"
                            placeholder="Age (optional)"
                            className="p-3 rounded-xl bg-white/10 border border-purple-300/30 text-white focus:outline-none focus:ring-2 focus:ring-purple-400"
                            value={form.age}
                            onChange={(e) => handleChange("age", e.target.value)}
                            min={0}
                            max={99}
                        />

                        <button
                            type="submit"
                            className="mt-4 px-6 py-3 rounded-2xl bg-gradient-to-br from-purple-600/80 to-purple-500/70 text-white font-semibold shadow-lg hover:shadow-purple-400/50 transition-all duration-300"
                        >
                            Search 🎃
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}
