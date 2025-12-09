"use client";

import React, { useState } from "react";

const scaryLevels = ["spooky", "funny", "cute", "classic", "dark", "sexy"];
const budgets = ["low", "medium", "high"];
const genders = ["unisex", "feminine", "masculine"];
const timeToPrep = ["last-minute", "quick", "medium effort", "high effort"];
const makeUpOptions = ["no", "light", "full"];
const maskOptions = ["yes", "no"];
const groupTypes = ["solo", "couple", "group", "family-friendly"];
const themes = [
    "vampires", "witches", "zombies", "ghosts", "skeletons", "demons",
    "monsters", "werewolves", "mummies", "movie, cartoon, game characters",
    "villains", "superheroes", "sci-fi characters", "fantasy characters",
    "animals", "food-themed costumes", "memes", "retro", "clowns",
    "historical", "professions", "DIY"
];
const colors = ["black", "red", "white", "pink", "pastel", "green", "blue", "neon"];
const ageGroups = ["child", "teen", "adult"];

export default function AddForm() {
    const [form, setForm] = useState<any>({
        image: null,
        name: "",
        description: "",
        scaryLevel: "",
        budget: "",
        gender: "",
        timePrep: "",
        makeUp: "",
        mask: "",
        groupType: "",
        themes: [] as string[],
        colors: [] as string[],
        tempMin: "",
        tempMax: "",
        ageGroup: ""
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

        if (form.themes.length === 0) {
            alert("⚠️ Please select at least one theme");
            return;
        }

        if (form.colors.length === 0) {
            alert("⚠️ Please select at least one color");
            return;
        }

        try {
            // ✅ 1. Загружаем картинку
            const formData = new FormData();
            formData.append("file", form.image);

            const uploadResponse = await fetch("http://localhost:8080/costume/uploadImage", {
                method: "POST",
                body: formData
            });

            if (!uploadResponse.ok) {
                throw new Error("Image upload failed");
            }

            const uploadResult = await uploadResponse.json();
            const imageUrl = uploadResult.imageUrl;

            // ✅ 2. Собираем объект костюма для БД
            const costumePayload = {
                name: form.name,
                description: form.description,
                age: form.ageGroup,
                minTemperature: Number(form.tempMin),
                maxTemperature: Number(form.tempMax),
                budget: form.budget,
                colors: form.colors,
                gender: form.gender,
                groupType: form.groupType,
                scaryLevel: form.scaryLevel,
                themes: form.themes,
                timeToPrep: form.timePrep,
                makeUp: form.makeUp,
                mask: form.mask,
                imageurl: imageUrl
            };

            // ✅ 3. Сохраняем костюм в БД
            const saveResponse = await fetch("http://localhost:8080/costume/save", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(costumePayload)
            });

            if (!saveResponse.ok) {
                throw new Error("Saving costume failed");
            }

            // ✅ 4. Уведомление об успехе
            alert("✅ Costume successfully saved!");

            window.location.reload();

        } catch (error) {
            console.error("❌ Error while saving costume:", error);
            alert("❌ Error while saving costume. Check console.");
        }

    };

    return (
        <div className="p-8">
            <div className="backdrop-blur-2xl bg-gradient-to-br from-white/10 to-white/5 border border-orange-400/30 rounded-3xl p-8 shadow-2xl relative overflow-hidden group hover:border-orange-400/60 transition-all duration-500">
                <div className="absolute inset-0 bg-gradient-to-br from-orange-500/0 via-orange-500/5 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>
                <div className="absolute -top-24 -right-24 w-48 h-48 bg-orange-500/20 rounded-full blur-3xl"></div>

                <div className="relative z-10">
                    <h2 className="text-3xl font-bold text-white mb-6 flex items-center gap-3">
                        <span className="text-4xl">✨</span> Add New Costume
                    </h2>

                    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                        {/* Image upload */}
                        <div className="flex flex-col">
                            <label className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white cursor-pointer text-center transition-all duration-300 hover:bg-white/20">
                                {form.image ? form.image.name : "Upload Costume Image (JPEG)"}
                                <input
                                    type="file"
                                    accept="image/jpeg"
                                    onChange={(e) => handleChange("image", e.target.files?.[0] || null)}
                                    className="hidden"
                                    required
                                />
                            </label>
                        </div>

                        {/* Name and Description */}
                        <input
                            type="text"
                            placeholder="Costume Name"
                            value={form.name}
                            onChange={(e) => handleChange("name", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        />
                        <textarea
                            placeholder="Costume Description"
                            value={form.description}
                            onChange={(e) => handleChange("description", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        />

                        {/* Multiple-choice selects */}
                        <select
                            value={form.scaryLevel}
                            onChange={(e) => handleChange("scaryLevel", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        >
                            <option value="">Select Scary Level</option>
                            {scaryLevels.map((lvl) => <option key={lvl} value={lvl}>{lvl}</option>)}
                        </select>

                        <select
                            value={form.budget}
                            onChange={(e) => handleChange("budget", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        >
                            <option value="">Select Budget</option>
                            {budgets.map((b) => <option key={b} value={b}>{b}</option>)}
                        </select>

                        <select
                            value={form.gender}
                            onChange={(e) => handleChange("gender", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        >
                            <option value="">Select Gender Presentation</option>
                            {genders.map((g) => <option key={g} value={g}>{g}</option>)}
                        </select>

                        <select
                            value={form.timePrep}
                            onChange={(e) => handleChange("timePrep", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        >
                            <option value="">Select Time to Prep</option>
                            {timeToPrep.map((t) => <option key={t} value={t}>{t}</option>)}
                        </select>

                        <select
                            value={form.makeUp}
                            onChange={(e) => handleChange("makeUp", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        >
                            <option value="">Select Make Up</option>
                            {makeUpOptions.map((m) => <option key={m} value={m}>{m}</option>)}
                        </select>

                        <select
                            value={form.mask}
                            onChange={(e) => handleChange("mask", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        >
                            <option value="">Do you want a mask?</option>
                            {maskOptions.map((m) => <option key={m} value={m}>{m}</option>)}
                        </select>

                        {/* Group Type */}
                        <select
                            value={form.groupType}
                            onChange={(e) => handleChange("groupType", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        >
                            <option value="">Select Group Type</option>
                            {groupTypes.map((g) => <option key={g} value={g}>{g}</option>)}
                        </select>

                        {/* Age group (сразу после Group Type) */}
                        <select
                            value={form.ageGroup}
                            onChange={(e) => handleChange("ageGroup", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            required
                        >
                            <option value="">Select Age Group</option>
                            {ageGroups.map((ag) => <option key={ag} value={ag}>{ag}</option>)}
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
                                        form.themes.includes(t) ? "bg-orange-500 text-white" : "border-orange-400/50 text-orange-200"
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
                                        form.colors.includes(c) ? "bg-orange-500 text-white" : "border-orange-400/50 text-orange-200"
                                    } transition-all duration-300`}
                                >
                                    {c}
                                </button>
                            ))}
                        </div>

                        {/* Temperature */}
                        <input
                            type="number"
                            placeholder="Min Temperature (°C)"
                            value={form.tempMin}
                            onChange={(e) => handleChange("tempMin", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            min={-10}
                            max={30}
                            required
                        />
                        <input
                            type="number"
                            placeholder="Max Temperature (°C)"
                            value={form.tempMax}
                            onChange={(e) => handleChange("tempMax", e.target.value)}
                            className="p-3 rounded-xl bg-white/10 border border-orange-300/30 text-white focus:outline-none focus:ring-2 focus:ring-orange-400"
                            min={-10}
                            max={30}
                            required
                        />

                        <button
                            type="submit"
                            className="mt-4 px-6 py-3 rounded-2xl bg-gradient-to-br from-orange-600/80 to-orange-500/70 text-white font-semibold shadow-lg hover:shadow-orange-400/50 transition-all duration-300"
                        >
                            Add Costume ✨
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}